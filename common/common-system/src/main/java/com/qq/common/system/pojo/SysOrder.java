package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单表
 *
 * @TableName sys_order
 */
@TableName(value = "sys_order")
@Data
@ApiModel("订单基本信息")
public class SysOrder implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "订单ID", hidden = true)
    private Long id;

    /**
     * 订单号
     */
    @TableField(value = "number")
    @ApiModelProperty(value = "订单号",hidden = true)
    private String number;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 总金额
     */
    @TableField(value = "total_amount")
    @ApiModelProperty("总金额")
    private BigDecimal totalAmount;

    /**
     * 总数量
     */
    @TableField(value = "total_count")
    @ApiModelProperty("总数量")
    private Integer totalCount;

    /**
     * 状态 0：已删除，1：正常
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态", hidden = true)
    private Integer status;

    /**
     * 状态 0：待付款，1：代发货，2：待收货，3：待评价，4：退款/售后，5：关闭
     */
    @TableField(value = "order_status")
    @ApiModelProperty(value = "订单状态", hidden = true)
    private Integer orderStatus;

    /**
     * 收货地址id
     */
    @TableField(value = "user_address_id")
    @ApiModelProperty("收货地址id")
    private Long userAddressId;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新者", hidden = true)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty("备注")
    private String remark;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private String addressName;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品数据", hidden = true)
    private List<SysOrderDetail> orderDetailList;

    @TableField(exist = false)
    @ApiModelProperty(value = "收货地址", hidden = true)
    private SysUserAddress address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}