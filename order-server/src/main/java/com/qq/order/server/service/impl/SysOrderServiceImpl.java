package com.qq.order.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.rabbit.config.ProductTopicConfig;
import com.qq.common.rabbit.handler.PushHandler;
import com.qq.common.rabbit.pojo.PushData;
import com.qq.common.system.pojo.*;
import com.qq.common.system.utils.OauthUtils;
import com.qq.order.server.mapper.SysOrderDetailMapper;
import com.qq.order.server.mapper.SysOrderMapper;
import com.qq.order.server.pojo.OrderQuery;
import com.qq.order.server.service.*;
import com.qq.order.server.vo.OrderVO;
import com.qq.order.server.vo.StatusCountVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【sys_order(订单表)】的数据库操作Service实现
 * @createDate 2022-05-06 16:44:17
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SysOrderServiceImpl extends ServiceImpl<SysOrderMapper, SysOrder>
        implements SysOrderService {

    private final SysOrderDetailMapper sysOrderDetailMapper;
    private final AccountService accountService;
    private final ShoppingCartItemService shoppingCartItemService;
    private final SkuService skuService;
    private final UserFeignService userService;
    private final MessageFeignService messageFeignService;
    private final PushHandler pushHandler;

    /**
     * 保存订单
     * @param orderVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrder(OrderVO orderVO) {
        SysOrder order = orderVO.getOrder();
        if (order == null) {
            throw new ServiceException("订单不能为空");
        }
        if (order.getUserId() == null) {
            throw new ServiceException("用户不能为空");
        }
        List<SysOrderDetail> orderDetailList = orderVO.getOrderDetailList();
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new ServiceException("商品信息不能为空");
        }
        Long accountId = orderVO.getAccountId();
        if (accountId == null) {
            // 未支付
            order.setOrderStatus(0);
        } else {
            // 已支付
            order.setOrderStatus(1);
        }
        String currentUserName = OauthUtils.getCurrentUserName();
        Date now = new Date();
        //保存订单
        order.setCreateBy(currentUserName);
        order.setCreateTime(now);
        order.setNumber("SO" + IdUtil.getSnowflakeNextId());
        this.baseMapper.insert(order);
        List<Long> skuIds = new ArrayList<>(orderDetailList.size());
        //保存订单详情
        for (SysOrderDetail orderDetail : orderDetailList) {
            orderDetail.setMasterId(order.getId());
            orderDetail.setCreateBy(currentUserName);
            orderDetail.setCreateTime(now);
            sysOrderDetailMapper.insert(orderDetail);
            //扣减库存
            AjaxResult ajaxResult = skuService.reduceStock(orderDetail.getSkuId(), orderDetail.getCount());
            if (ajaxResult.getCode() != HttpStatus.OK.value()) {
                throw new ServiceException(ajaxResult.getMsg());
            }
            skuIds.add(orderDetail.getSkuId());
        }
        if (accountId != null) {
            // 扣减账户余额
            AjaxResult ajaxResult = accountService.operateAccountAmount(accountId, order.getTotalAmount().negate());
            if (ajaxResult.getCode() != HttpStatus.OK.value()) {
                throw new ServiceException(ajaxResult.getMsg());
            }
        }
        // 删除选中的购物车商品
        if(CollUtil.isNotEmpty(orderVO.getCartIds())){
            shoppingCartItemService.removeByIds(orderVO.getCartIds());
        }
        // 更新热卖信息
        updateHotSale(skuIds);
        return order.getId();
    }

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    @Override
    public SysOrder getOrderInfo(Long orderId) {
        if (orderId == null) {
            throw new ServiceException("订单ID不能为空");
        }
        SysOrder order = this.baseMapper.selectById(orderId);
        List<SysOrderDetail> details = sysOrderDetailMapper.selectList(new QueryWrapper<SysOrderDetail>().eq("master_id", orderId));
        for (SysOrderDetail detail : details) {
            AjaxResult ajaxResult = skuService.getSkuById(detail.getSkuId());
            if (ajaxResult.getCode() == HttpStatus.OK.value()) {
                SysSku sku = BeanUtil.mapToBean((Map<String, Object>) ajaxResult.getData(), SysSku.class, true, null);
                detail.setSku(sku);
            } else {
                throw new ServiceException(ajaxResult.getMsg());
            }
        }
        AjaxResult ajaxResult = userService.queryAddressById(order.getUserAddressId());
        if (ajaxResult.getCode() != HttpStatus.OK.value()) {
            throw new ServiceException(ajaxResult.getMsg());
        }
        order.setOrderDetailList(details);
        SysUserAddress sysUserAddress = BeanUtil.mapToBean((Map<String, Object>) ajaxResult.getData(), SysUserAddress.class, true, null);
        order.setAddress(sysUserAddress);
        return order;
    }

    /**
     * 查询订单列表
     * @param query
     * @return
     */
    @Override
    public List<SysOrder> list(OrderQuery query) {
        List<SysOrder> list = this.baseMapper.list(query);
        for (SysOrder order : list) {
            List<SysOrderDetail> details = order.getOrderDetailList();
            for (SysOrderDetail detail : details) {
                AjaxResult ajaxResult = skuService.getSkuById(detail.getSkuId());
                if (ajaxResult.getCode() == HttpStatus.OK.value()) {
                    SysSku sku = BeanUtil.mapToBean((Map<String, Object>) ajaxResult.getData(), SysSku.class, true, null);
                    detail.setSku(sku);
                } else {
                    throw new ServiceException(ajaxResult.getMsg());
                }
            }
        }
        return list;
    }

    /**
     * 查询订单各种状态数量
     * @param userId
     * @return
     */
    @Override
    public List<StatusCountVO> getStatusCount(Long userId) {
        return this.baseMapper.getStatusCount(userId);
    }

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Long orderId, Integer orderStatus) {
        SysOrder order = this.baseMapper.selectById(orderId);
        if(ObjectUtil.isEmpty(order)){
            throw new ServiceException("订单不存在！");
        }
        SysOrder updateOrder = new SysOrder();
        updateOrder.setId(orderId);
        updateOrder.setOrderStatus(orderStatus);
        this.baseMapper.updateById(updateOrder);

        String msgBody = null;
        // 状态 0：待付款，1：代发货，2：待收货，3：待评价，4：退款/售后
        if(2 == orderStatus){
            msgBody = String.format("您的订单：%s 已发货", order.getNumber());
        }else  if(3 == orderStatus){
            // 生成待评价记录
            List<SysOrderDetail> details = sysOrderDetailMapper.selectList(new QueryWrapper<SysOrderDetail>().eq("master_id", orderId));
            List<SysSkuEvaluation> list = new ArrayList<>(details.size());
            for (SysOrderDetail detail:details){
                SysSkuEvaluation skuEvaluation = new SysSkuEvaluation();
                skuEvaluation.setOrderId(orderId);
                skuEvaluation.setOrderDetailId(detail.getId());
                skuEvaluation.setSkuId(detail.getSkuId());
                skuEvaluation.setUserId(order.getUserId());
                list.add(skuEvaluation);
            }
            createOrderEvaluation(list);
            msgBody = String.format("您的订单：%s 已送达请及时签收", order.getNumber());
        }else if(4 == orderStatus){
            msgBody = String.format("您的订单：%s 售后申请已处理", order.getNumber());
        }
        // 推送订单消息
        if(StrUtil.isNotEmpty(msgBody)){
            SysMessage message = new SysMessage();
            message.setUserId(order.getUserId());
            message.setAction("business");
            message.setType("0");
            message.setNotificationType("primary");
            message.setBody(msgBody);
            message.setRedirectUrl("/orderDetail");
            messageFeignService.sendOneMessage(message);
        }

    }

    /**
     * 更新热卖信息
     *
     * @param skuIds
     */
    private void updateHotSale(List<Long> skuIds) {
        PushData<List<Long>> pushData = new PushData<>();
        pushData.setTopicName(ProductTopicConfig.TOPIC_NAME);
        pushData.setRoutingKey(ProductTopicConfig.HOT_SALE_ROUTING_KEY);
        pushData.setData(skuIds);
        pushHandler.pushData(pushData);
    }

    /**
     * 生成待评价记录
     *
     * @param list
     */
    private void createOrderEvaluation(List<SysSkuEvaluation> list){
        PushData<List<SysSkuEvaluation>> pushData = new PushData<>();
        pushData.setTopicName(ProductTopicConfig.TOPIC_NAME);
        pushData.setRoutingKey(ProductTopicConfig.SKU_EVALUATION_ROUTING_KEY);
        pushData.setData(list);
        pushHandler.pushData(pushData);
    }

}




