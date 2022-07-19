package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 属性表
 *
 * @TableName sys_attribute
 */
@TableName(value = "sys_attribute")
@Data
@ApiModel("商品属性基本信息")
public class SysAttribute implements Serializable {
    /**
     * 属性ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "属性ID", hidden = true)
    private Long id;

    /**
     * 属性名称
     */
    @TableField(value = "name")
    @ApiModelProperty("属性名称")
    private String name;

    /**
     * 属性值
     */
    @TableField(exist = false)
    @ApiModelProperty("属性值")
    private List<SysAttributeValue> values;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}