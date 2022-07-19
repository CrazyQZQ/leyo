package com.qq.order.server.vo;

import com.qq.common.system.pojo.SysOrder;
import com.qq.common.system.pojo.SysOrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/13
 **/
@Data
@ApiModel("订单保存参数")
public class OrderVO {

    /**
     * 主表数据
     */
    @ApiModelProperty("订单数据")
    private SysOrder order;

    /**
     * 子表数据
     */
    @ApiModelProperty("商品数据")
    private List<SysOrderDetail> orderDetailList;

    /**
     * 账户id
     */
    @ApiModelProperty("账户id")
    private Long accountId;
}
