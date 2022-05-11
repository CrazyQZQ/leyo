package com.qq.product.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品图片表
 * @TableName sys_product_images
 */
@TableName(value ="sys_product_images")
@Data
public class SysProductImages implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 图片url
     */
    @TableField(value = "image_url")
    private String imageUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}