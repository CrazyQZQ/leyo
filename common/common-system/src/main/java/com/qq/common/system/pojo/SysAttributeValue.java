package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品属性值表
 * @TableName sys_attribute_value
 */
@TableName(value ="sys_attribute_value")
@Data
public class SysAttributeValue implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性值名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 属性id
     */
    @TableField(value = "attribute_id")
    private Long attributeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}