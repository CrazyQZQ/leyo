package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 用户和角色关联表(SysUserRole)实体类
 *
 * @author makejava
 * @since 2022-04-08 17:12:19
 */
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -89769862856507650L;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 角色ID
    */
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}