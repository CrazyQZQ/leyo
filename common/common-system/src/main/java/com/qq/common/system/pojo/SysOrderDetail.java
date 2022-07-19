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

/**
 * 订单详情表
 *
 * @TableName sys_order_detail
 */
@TableName(value = "sys_order_detail")
@Data
@ApiModel("订单商品信息")
public class SysOrderDetail implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "子表ID", hidden = true)
    private Long id;

    /**
     * 主表id
     */
    @TableField(value = "master_id")
    @ApiModelProperty(value = "主表id", hidden = true)
    private Long masterId;

    /**
     * 商品skuId
     */
    @TableField(value = "sku_id")
    @ApiModelProperty("商品skuId")
    private Long skuId;

    /**
     * 数量
     */
    @TableField(value = "count")
    @ApiModelProperty("数量")
    private Integer count;

    /**
     * 金额
     */
    @TableField(value = "amount")
    @ApiModelProperty("金额")
    private BigDecimal amount;

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
    private SysSku sku;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}