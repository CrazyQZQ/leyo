package com.qq.order.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.*;
import com.qq.common.system.utils.OauthUtils;
import com.qq.order.server.mapper.SysOrderDetailMapper;
import com.qq.order.server.mapper.SysOrderMapper;
import com.qq.order.server.pojo.OrderQuery;
import com.qq.order.server.service.AccountService;
import com.qq.order.server.service.ProductService;
import com.qq.order.server.service.SkuService;
import com.qq.order.server.service.SysOrderService;
import com.qq.order.server.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private final SkuService skuService;

    @Override
    @Transactional
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
            order.setStatus(0);
        } else {
            order.setStatus(1);
        }
        String currentUserName = OauthUtils.getCurrentUserName();
        Date now = new Date();
        //保存订单
        order.setCreateBy(currentUserName);
        order.setCreateTime(now);
        order.setNumber("SO" + IdUtil.getSnowflakeNextId());
        this.baseMapper.insert(order);
        //保存订单详情
        for (SysOrderDetail orderDetail : orderDetailList) {
            orderDetail.setMasterId(order.getId());
            orderDetail.setCreateBy(currentUserName);
            orderDetail.setCreateTime(now);
            sysOrderDetailMapper.insert(orderDetail);
            //扣减库存
            AjaxResult ajaxResult = skuService.reduceStock(orderDetail.getSkuId(), orderDetail.getCount());
            if (!ajaxResult.isSuccess()) {
                throw new ServiceException(ajaxResult.getMessage());
            }
        }
        if (accountId != null) {
            // 扣减账户余额
            AjaxResult ajaxResult = accountService.operateAccountAmount(accountId, order.getTotalAmount().negate());
            if (!ajaxResult.isSuccess()) {
                throw new ServiceException(ajaxResult.getMessage());
            }
        }
        return order.getId();
    }

    @Override
    public OrderVO getOrderInfo(Long orderId) {
        if (orderId == null) {
            throw new ServiceException("订单ID不能为空");
        }
        SysOrder order = this.baseMapper.selectById(orderId);
        List<SysOrderDetail> details = sysOrderDetailMapper.selectList(new QueryWrapper<SysOrderDetail>().eq("master_id", orderId));
        for (SysOrderDetail detail : details) {
            AjaxResult ajaxResult = skuService.getSkuById(detail.getSkuId());
            if (ajaxResult.isSuccess()) {
                SysSku sku = BeanUtil.mapToBean((Map<String, Object>) ajaxResult.getData(), SysSku.class, true, null);
                detail.setSku(sku);
            } else {
                throw new ServiceException(ajaxResult.getMessage());
            }
        }
        OrderVO orderVO = new OrderVO();
        orderVO.setOrder(order);
        orderVO.setOrderDetailList(details);
        return orderVO;
    }

    @Override
    public List<SysOrder> list(OrderQuery query) {
        List<SysOrder> list = this.baseMapper.list(query);
        for (SysOrder order : list) {
            List<SysOrderDetail> details = order.getOrderDetailList();
            for (SysOrderDetail detail : details) {
                AjaxResult ajaxResult = skuService.getSkuById(detail.getSkuId());
                if (ajaxResult.isSuccess()) {
                    SysSku sku = BeanUtil.mapToBean((Map<String, Object>) ajaxResult.getData(), SysSku.class, true, null);
                    detail.setSku(sku);
                } else {
                    throw new ServiceException(ajaxResult.getMessage());
                }
            }
        }
        return list;
    }
}




