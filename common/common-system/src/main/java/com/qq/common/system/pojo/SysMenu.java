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
 * 菜单权限表
 *
 * @TableName sys_menu
 */
@TableName(value = "sys_menu")
@Data
@ApiModel("菜单基本信息")
public class SysMenu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    @ApiModelProperty("菜单ID")
    private Long menuId;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    @ApiModelProperty("菜单名称")
    private String menuName;

    /**
     * 父菜单ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty("父菜单ID")
    private Long parentId;

    /**
     * 显示顺序
     */
    @TableField(value = "order_num")
    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @TableField(value = "path")
    @ApiModelProperty("路由地址")
    private String path;

    /**
     * 组件路径
     */
    @TableField(value = "component")
    @ApiModelProperty("组件路径")
    private String component;

    /**
     * 路由参数
     */
    @TableField(value = "query")
    @ApiModelProperty("路由参数")
    private String query;

    /**
     * 是否为外链（0是 1否）
     */
    @TableField(value = "is_frame")
    @ApiModelProperty("是否为外链（0是 1否）")
    private Integer isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @TableField(value = "is_cache")
    @ApiModelProperty("是否缓存（0缓存 1不缓存）")
    private Integer isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @TableField(value = "menu_type")
    @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @TableField(value = "visible")
    @ApiModelProperty("菜单状态（0显示 1隐藏）")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @TableField(value = "status")
    @ApiModelProperty("菜单状态（0正常 1停用）")
    private String status;

    /**
     * 权限标识
     */
    @TableField(value = "perms")
    @ApiModelProperty("权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    @ApiModelProperty("用户ID")
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

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty("备注")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}