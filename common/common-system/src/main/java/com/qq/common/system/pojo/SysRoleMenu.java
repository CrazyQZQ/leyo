package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色和菜单关联表
 * @TableName sys_role_menu
 */
@TableName(value ="sys_role_menu")
@Data
public class SysRoleMenu implements Serializable {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableId
    private Long menuId;

    /**
     * 角色权限字符串
     */
    @TableField(exist = false)
    private String roleKey;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}