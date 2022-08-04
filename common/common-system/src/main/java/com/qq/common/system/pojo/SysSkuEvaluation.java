package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评价表
 * @TableName sys_sku_evaluation
 */
@TableName(value ="sys_sku_evaluation")
@Data
@ApiModel("评价基本信息")
public class SysSkuEvaluation implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    /**
     * 状态：0：已删除，1：正常
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态：0：已删除，1：正常", hidden = true)
    private Integer status;

    /**
     * 评价状态：0：未评价，1：已评价
     */
    @TableField(value = "evaluate_status")
    @ApiModelProperty("评价状态：0：未评价，1：已评价")
    private Integer evaluateStatus;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 订单主表id
     */
    @TableField(value = "order_id")
    @ApiModelProperty("订单主表id")
    private Long orderId;

    /**
     * 订单子表id
     */
    @TableField(value = "order_detail_id")
    @ApiModelProperty("订单子表id")
    private Long orderDetailId;

    /**
     * sku_id
     */
    @TableField(value = "sku_id")
    @ApiModelProperty("skuId")
    private Long skuId;

    /**
     * 描述相符，星级：1-5
     */
    @TableField(value = "description_matches")
    @ApiModelProperty("描述相符，星级：1-5")
    private Integer descriptionMatches;

    /**
     * 物流服务，星级：1-5
     */
    @TableField(value = "Logistics_services")
    @ApiModelProperty("物流服务，星级：1-5")
    private Integer logisticsServices;

    /**
     * 服务态度，星级：1-5
     */
    @TableField(value = "service_attitude")
    @ApiModelProperty("服务态度，星级：1-5")
    private Integer serviceAttitude;

    /**
     * 评价内容
     */
    @TableField(value = "comment")
    @ApiModelProperty("评价内容")
    private String comment;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;

    /**
     * 用户
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private SysUser user;

    /**
     * sku
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private SysSku sku;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}