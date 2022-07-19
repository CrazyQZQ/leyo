package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色和菜单关联表
 *
 * @TableName sys_role_menu
 */
@TableName(value = "sys_role_menu")
@Data
@ApiModel("角色和菜单关联信息")
public class SysRoleMenu implements Serializable {
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Long roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    @TableId
    private Long menuId;

    /**
     * 角色权限字符串
     */
    @ApiModelProperty("角色权限字符串（如：admin）")
    @TableField(exist = false)
    private String roleKey;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}