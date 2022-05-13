package com.qq.order.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysOrder;
import com.qq.order.server.vo.ProductVO;

/**
* @author Administrator
* @description 针对表【sys_order(订单表)】的数据库操作Service
* @createDate 2022-05-06 16:44:17
*/
public interface SysOrderService extends IService<SysOrder> {

    Long saveOrder(ProductVO productVO);

    ProductVO getOrderInfo(Long orderId);
}
