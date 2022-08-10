package com.qq.order.server.pojo;

import com.qq.common.core.web.page.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/6/8
 **/
@Data
@ApiModel("订单查询条件")
public class OrderQuery extends BaseQuery {
    @ApiModelProperty("订单状态")
    private Integer orderStatus;
    @ApiModelProperty("用户ID")
    private Long userId;
}
