package com.qq.order.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysOrder;
import com.qq.order.server.pojo.OrderQuery;
import com.qq.order.server.vo.OrderVO;
import com.qq.order.server.vo.StatusCountVO;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_order(订单表)】的数据库操作Service
 * @createDate 2022-05-06 16:44:17
 */
public interface SysOrderService extends IService<SysOrder> {

    /**
     * 保存订单
     * @param orderVO
     * @return
     */
    Long saveOrder(OrderVO orderVO);

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    SysOrder getOrderInfo(Long orderId);

    /**
     * 查询订单列表
     * @param query
     * @return
     */
    List<SysOrder> list(OrderQuery query);

    /**
     * 查询订单各种状态数量
     * @param userId
     * @return
     */
    List<StatusCountVO> getStatusCount(Long userId);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(Long orderId, Integer orderStatus);
}
