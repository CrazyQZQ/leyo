package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 购物车清单
 *
 * @TableName shopping_cart_item
 */
@TableName(value = "shopping_cart_item")
@Data
public class ShoppingCartItem implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * skuId
     */
    @TableField(value = "sku_id")
    private Long skuId;

    /**
     * 数量
     */
    @TableField(value = "num")
    private Integer num;

    @TableField(exist = false)
    private SysSku sku;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}