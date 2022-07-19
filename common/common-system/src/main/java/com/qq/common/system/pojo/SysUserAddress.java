package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户地址表
 *
 * @TableName sys_user_address
 */
@TableName(value = "sys_user_address")
@Data
@ApiModel("用户基本信息")
public class SysUserAddress implements Serializable {
    /**
     * 地址id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("地址id")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 收货人姓名
     */
    @TableField(value = "receiver_name")
    @ApiModelProperty("收货人姓名")
    private String receiverName;

    /**
     * 收货人手机号
     */
    @TableField(value = "receiver_tel")
    @ApiModelProperty("收货人手机号")
    private String receiverTel;

    /**
     * 默认地址：0-否，1-是
     */
    @TableField(value = "default_status")
    @ApiModelProperty("默认地址：0-否，1-是")
    private Integer defaultStatus;

    /**
     * 邮编
     */
    @TableField(value = "post_code")
    @ApiModelProperty("邮编")
    private String postCode;

    /**
     * 省份
     */
    @TableField(value = "province")
    @ApiModelProperty("省份")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    @ApiModelProperty("城市")
    private String city;

    /**
     * 区
     */
    @TableField(value = "region")
    @ApiModelProperty("区")
    private String region;

    /**
     * 区域编码，到区级
     */
    @TableField(value = "area_code")
    @ApiModelProperty("区域编码，到区级")
    private String areaCode;

    /**
     * 详细地址
     */
    @TableField(value = "detail_address")
    @ApiModelProperty("详细地址")
    private String detailAddress;

    /**
     * 完整地址
     */
    @TableField(value = "full_address")
    @ApiModelProperty("完整地址")
    private String fullAddress;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}