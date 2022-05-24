package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户地址表
 * @TableName sys_user_address
 */
@TableName(value ="sys_user_address")
@Data
public class SysUserAddress implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 收货人姓名
     */
    @TableField(value = "receiver_name")
    private String receiverName;

    /**
     * 收货人手机号
     */
    @TableField(value = "receiver_tel")
    private String receiverTel;

    /**
     * 默认地址：0-否，1-是
     */
    @TableField(value = "default_status")
    private Integer defaultStatus;

    /**
     * 邮编
     */
    @TableField(value = "post_code")
    private String postCode;

    /**
     * 省份
     */
    @TableField(value = "province")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 区域编码，到区级
     */
    @TableField(value = "area_code")
    private String areaCode;

    /**
     * 详细地址
     */
    @TableField(value = "detail_address")
    private String detailAddress;

    /**
     * 完整地址
     */
    @TableField(value = "full_address")
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