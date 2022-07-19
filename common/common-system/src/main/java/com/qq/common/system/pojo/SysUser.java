package com.qq.common.system.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@ApiModel("用户基本信息")
public class SysUser extends JwtInfo implements Serializable {
    private static final long serialVersionUID = 553069093666489502L;
    /**
     * 用户ID
     */
    @TableId
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long deptId;
    /**
     * 登录账号
     */
    @NotEmpty(message = "用户名不能为空！")
    @ApiModelProperty("用户名")
    private String userName;
    /**
     * 用户昵称
     */
    @ApiModelProperty("用户名")
    private String nickName;
    /**
     * 用户类型（00系统用户 01注册用户）
     */
    @ApiModelProperty("用户名")
    private String userType;
    /**
     * 用户邮箱
     */
    @Email
    @ApiModelProperty("用户名")
    private String email;
    /**
     * 手机号码
     */
    @ApiModelProperty("用户名")
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "手机号格式错误！")
    private String phoneNumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty("性别（0男 1女 2未知）")
    private String sex;
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @JSONField(serialize = false)
    @NotEmpty(message = "密码不能为空！")
    private String password;
    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty("用户名")
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
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
}