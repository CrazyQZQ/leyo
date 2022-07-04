package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qq.common.system.vo.SkuAttributeVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品sku表
 * @TableName sys_sku
 */
@TableName(value ="sys_sku")
@Data
public class SysSku implements Serializable {
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
     * 规格属性(属性id：属性值id)
     */
    @TableField(value = "spec")
    private String spec;

    /**
     * 价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 原价
     */
    @TableField(value = "original_price")
    private BigDecimal originalPrice;

    /**
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 销量
     */
    @TableField(value = "sales")
    private Integer sales;

    /**
     * 图片
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String productName;

    /**
     * 品牌名称
     */
    @TableField(exist = false)
    private String brandName;

    /**
     * 品类名称
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 属性展平字符串
     */
    @TableField(exist = false)
    private String skuAttributeStr;

    /**
     * 属性
     */
    @TableField(exist = false)
    private List<SkuAttributeVO> skuAttributes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}