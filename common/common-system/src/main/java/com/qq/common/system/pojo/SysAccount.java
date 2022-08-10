package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户表
 *
 * @TableName sys_account
 */
@TableName(value = "sys_account")
@Data
@ApiModel("账户基本信息")
public class SysAccount implements Serializable {
    /**
     * 账号id
     */
    @TableId(value = "account_id", type = IdType.AUTO)
    @ApiModelProperty("账号id")
    private Long accountId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空！")
    private Long userId;

    /**
     * 账户编码
     */
    @TableField(value = "account_code")
    @ApiModelProperty("账户编码")
    @NotNull(message = "账户编码不能为空！")
    private String accountCode;

    /**
     * 账号名称
     */
    @TableField(value = "account_name")
    @ApiModelProperty("账号名称")
    @NotNull(message = "账号名称不能为空！")
    private String accountName;

    /**
     * 部门名称
     */
    @TableField(value = "amount")
    @ApiModelProperty("余额")
    private BigDecimal amount;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    @ApiModelProperty("创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    @ApiModelProperty("更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}