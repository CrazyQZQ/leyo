package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qq.common.system.vo.SkuAttributeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品sku表
 *
 * @TableName sys_sku
 */
@TableName(value = "sys_sku")
@Data
@ApiModel("商品sku基本信息")
public class SysSku implements Serializable {
    /**
     * skuId
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "skuId", hidden = true)
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    @ApiModelProperty("商品id")
    private Long productId;

    /**
     * 规格属性(属性id：属性值id)
     */
    @TableField(value = "spec")
    @ApiModelProperty("规格属性(属性id：属性值id)，如：[{\"keyId\":1,\"valId\":2},{\"keyId\":2,\"valId\":5}]")
    private String spec;

    /**
     * 价格
     */
    @TableField(value = "price")
    @ApiModelProperty("价格")
    private BigDecimal price;

    /**
     * 原价
     */
    @TableField(value = "original_price")
    @ApiModelProperty("原价")
    private BigDecimal originalPrice;

    /**
     * 库存
     */
    @TableField(value = "stock")
    @ApiModelProperty("库存")
    private Integer stock;

    /**
     * 销量
     */
    @TableField(value = "sales")
    @ApiModelProperty("销量")
    private Integer sales;

    /**
     * 图片
     */
    @TableField(value = "image_url")
    @ApiModelProperty(value = "图片", hidden = true)
    private String imageUrl;

    /**
     * 商品名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "商品名称", hidden = true)
    private String productName;

    /**
     * 品牌名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "品牌名称", hidden = true)
    private String brandName;

    /**
     * 品类名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "品类名称", hidden = true)
    private String typeName;

    /**
     * 属性展平字符串
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "属性展平字符串", hidden = true)
    private String skuAttributeStr;

    /**
     * 属性
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "商品属性", hidden = true)
    private List<SkuAttributeVO> skuAttributes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}