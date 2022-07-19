package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品图片表
 *
 * @TableName sys_object_images
 */
@TableName(value = "sys_object_images")
@Data
public class SysObjectImages implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "object_id")
    private Long objectId;

    /**
     * 图片url
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 对象类型，1：品类，2：品牌，3：商品
     */
    @TableField(value = "object_type")
    private Integer ObjectType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}