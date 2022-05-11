package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表(SysRole)实体类
 *
 * @author makejava
 * @since 2022-04-08 16:42:06
 */
@TableName("sys_role")
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 754946314696120551L;
    /**
    * 角色ID
    */
    @TableId
    private Long roleId;
    /**
    * 角色名称
    */
    private String roleName;
    /**
    * 角色权限字符串
    */
    private String roleKey;
    /**
    * 显示顺序
    */
    private Integer roleSort;
    /**
    * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
    */
    private String dataScope;
    /**
    * 菜单树选择项是否关联显示
    */
    private Integer menuCheckStrictly;
    /**
    * 部门树选择项是否关联显示
    */
    private Object deptCheckStrictly;
    /**
    * 角色状态（0正常 1停用）
    */
    private String status;
    /**
    * 删除标志（0代表存在 2代表删除）
    */
    private String delFlag;
    /**
    * 创建者
    */
    private String createBy;
    /**
    * 创建时间
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
    * 更新者
    */
    private String updateBy;
    /**
    * 更新时间
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /**
    * 备注
    */
    private String remark;
}