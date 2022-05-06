package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 商品表
 * @TableName sys_product
 */
@TableName(value ="sys_product")
@Data
public class SysProduct implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 商品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 单位
     */
    @TableField(value = "unit")
    private String unit;

    /**
     * 价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}