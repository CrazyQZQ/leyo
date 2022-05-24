package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 地区信息
 * @TableName sys_area_info
 */
@TableName(value ="sys_area_info")
@Data
public class SysAreaInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 上级id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 地区编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 地区名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 层级
     */
    @TableField(value = "level")
    private Integer level;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}