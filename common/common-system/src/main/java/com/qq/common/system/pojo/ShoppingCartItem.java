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
 * 购物车清单
 *
 * @TableName shopping_cart_item
 */
@TableName(value = "shopping_cart_item")
@Data
@ApiModel("购物车基本信息")
public class ShoppingCartItem implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * skuId
     */
    @TableField(value = "sku_id")
    @ApiModelProperty("skuId")
    private Long skuId;

    /**
     * 数量
     */
    @TableField(value = "num")
    @ApiModelProperty("数量")
    private Integer num;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private SysSku sku;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}