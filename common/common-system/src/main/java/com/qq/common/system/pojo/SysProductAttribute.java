package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品属性关联表
 * @TableName sys_product_attribute
 */
@TableName(value ="sys_product_attribute")
@Data
public class SysProductAttribute implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 属性id
     */
    @TableField(value = "attribute_id")
    private Long attributeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}