package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class SysAttribute implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 属性值
     */
    @TableField(exist = false)
    private List<SysAttributeValue> values;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}