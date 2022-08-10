package com.qq.order.server.vo;

import lombok.Data;

/**
 * @Description: 订单各状态数量
 * @Author QinQiang
 * @Date 2022/6/10
 **/
@Data
public class StatusCountVO {
    /**
     * 状态
     */
    private Integer orderStatus;
    /**
     * 数量
     */
    private Integer count;
}
