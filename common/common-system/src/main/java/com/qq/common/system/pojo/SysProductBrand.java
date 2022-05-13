package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品品牌关联表
 * @TableName sys_product_brand
 */
@TableName(value ="sys_product_brand")
@Data
public class SysProductBrand implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 品牌id
     */
    @TableField(value = "brand_id")
    private Long brandId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}