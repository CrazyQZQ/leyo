package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性值表
 *
 * @TableName sys_attribute_value
 */
@TableName(value = "sys_attribute_value")
@Data
@ApiModel("商品属性值基本信息")
public class SysAttributeValue implements Serializable {
    /**
     * 属性值ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "属性值ID", hidden = true)
    private Long id;

    /**
     * 属性值名称
     */
    @TableField(value = "name")
    @ApiModelProperty("属性值名称")
    private String name;

    /**
     * 属性id
     */
    @TableField(value = "attribute_id")
    @ApiModelProperty("属性id")
    private Long attributeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}