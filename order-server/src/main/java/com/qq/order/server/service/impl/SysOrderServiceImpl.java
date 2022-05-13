package com.qq.order.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.system.pojo.SysProduct;
import com.qq.common.system.utils.OauthUtils;
import com.qq.order.server.mapper.SysOrderDetailMapper;
import com.qq.order.server.mapper.SysOrderMapper;
import com.qq.common.system.pojo.SysOrder;
import com.qq.common.system.pojo.SysOrderDetail;
import com.qq.order.server.service.AccountService;
import com.qq.order.server.service.ProductService;
import com.qq.order.server.service.SysOrderService;
import com.qq.order.server.vo.ProductVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_order(订单表)】的数据库操作Service实现
 * @createDate 2022-05-06 16:44:17
 */
@Service
@AllArgsConstructor
@Slf4j
public class SysOrderServiceImpl extends ServiceImpl<SysOrderMapper, SysOrder>
        implements SysOrderService {

    private final SysOrderDetailMapper sysOrderDetailMapper;
    private final AccountService accountService;
    private final ProductService productService;

    @Override
    @Transactional
    public Long saveOrder(ProductVO productVO) {
        SysOrder order = productVO.getOrder();
        if (order == null) {
            throw new ServiceException("订单不能为空");
        }
        if (order.getUserId() == null) {
            throw new ServiceException("用户不能为空");
        }
        List<SysOrderDetail> orderDetailList = productVO.getOrderDetailList();
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new ServiceException("商品信息不能为空");
        }
        Long accountId = productVO.getAccountId();
        if (accountId == null) {
            throw new ServiceException("结算账户不能为空");
        }
        String currentUserName = OauthUtils.getCurrentUserName();
        Date now = new Date();
        //保存订单
        order.setCreateBy(currentUserName);
        order.setCreateTime(now);
        this.baseMapper.insert(order);
        //保存订单详情
        for (SysOrderDetail orderDetail : orderDetailList) {
            orderDetail.setMasterId(order.getId());
            orderDetail.setCreateBy(currentUserName);
            orderDetail.setCreateTime(now);
            sysOrderDetailMapper.insert(orderDetail);
            //扣减库存
            productService.reduceStock(orderDetail.getProductId(), orderDetail.getCount());
        }
        // 扣减账户余额
        accountService.operateAccountAmount(accountId, order.getTotalAmount().negate());
        return order.getId();
    }

    @Override
    public ProductVO getOrderInfo(Long orderId) {
        this.baseMapper.selectById(orderId);
        List<SysOrderDetail> details = sysOrderDetailMapper.selectList(new QueryWrapper<SysOrderDetail>().eq("master_id", orderId));
        List<Long> productIds = details.stream().map(SysOrderDetail::getProductId).collect(Collectors.toList());
        BaseQuery query = new BaseQuery();
        query.setIds(productIds);
        TableDataInfo tableDataInfo = productService.getProductList(query);
        List<SysProduct> products = (List<SysProduct>) tableDataInfo.getRows();
        for(SysOrderDetail detail : details){
            products.stream().filter(p -> p.getId().equals(detail.getProductId())).findFirst().ifPresent(p -> {
                detail.setProduct(p);
            });
        }
        return null;
    }
}




