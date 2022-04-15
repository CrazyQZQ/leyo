package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表(SysUser)实体类
 *
 * @author makejava
 * @since 2022-04-07 19:03:41
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser extends JwtInfo implements Serializable {
    private static final long serialVersionUID = 553069093666489502L;
    /**
     * 用户ID
     */
    @TableId
    private Long userId;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户类型（00系统用户 01注册用户）
     */
    private String userType;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐加密
     */
    private String salt;
    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 密码最后更新时间
     */
    private Date pwdUpdateDate;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 权限集合
     */
    @TableField(exist = false)
    private String[] authorities;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("userId", userId)
                .append("deptId", deptId)
                .append("loginName", loginName)
                .append("userName", userName)
                .append("userType", userType)
                .append("email", email)
                .append("phonenumber", phonenumber)
                .append("sex", sex)
                .append("avatar", avatar)
                .append("password", password)
                .append("salt", salt)
                .append("status", status)
                .append("delFlag", delFlag)
                .append("loginIp", loginIp)
                .append("loginDate", loginDate)
                .append("pwdUpdateDate", pwdUpdateDate)
                .append("createBy", createBy)
                .append("createTime", createTime)
                .append("updateBy", updateBy)
                .append("updateTime", updateTime)
                .append("remark", remark)
                .append("authorities", authorities)
                .toString();
    }
}