package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统消息表
 * @TableName sys_message
 */
@TableName(value ="sys_message")
@Data
@ApiModel("系统消息")
public class SysMessage implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "消息ID", hidden = true)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 操作
     */
    @TableField(value = "action")
    @ApiModelProperty("操作")
    private String action;

    /**
     * 消息类型 0：系统消息
     */
    @TableField(value = "type")
    @ApiModelProperty("消息类型 0：系统消息")
    private String type;

    /**
     * 通知类型 primary success warning danger
     */
    @TableField(value = "notification_type")
    @ApiModelProperty("通知类型 primary success warning danger")
    private String notificationType;

    /**
     * 消息内容
     */
    @TableField(value = "body")
    @ApiModelProperty("消息内容")
    private String body;

    /**
     * 跳转url
     */
    @TableField(value = "redirect_url")
    @ApiModelProperty("跳转url")
    private String redirectUrl;

    /**
     * 推送状态 0：未推送，1：推送成功，2：推送失败
     */
    @TableField(value = "push_status")
    @ApiModelProperty(value = "推送状态 0：未推送，1：推送成功，2：推送失败",hidden = true)
    private String pushStatus;

    /**
     * 是否已读 0：未读，1：已读
     */
    @TableField(value = "read_status")
    @ApiModelProperty(value = "是否已读 0：未读，1：已读",hidden = true)
    private String readStatus;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    @ApiModelProperty(hidden = true)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    @ApiModelProperty(hidden = true)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}