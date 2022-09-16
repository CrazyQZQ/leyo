package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品收藏表
 * @TableName sys_product_collection
 */
@TableName(value ="sys_product_collection")
@Data
@ApiModel("商品收藏基本信息")
public class SysProductCollection implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    /**
     * 状态：0：已删除，1：正常
     */
    @TableField(value = "status")
    @ApiModelProperty("状态：0：已删除，1：正常")
    private Integer status;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    @ApiModelProperty("商品id")
    private Long productId;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建者", hidden = true)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}