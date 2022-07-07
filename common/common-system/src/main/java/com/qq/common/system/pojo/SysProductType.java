package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品品类表
 *
 * @TableName sys_product_type
 */
@TableName(value = "sys_product_type")
@Data
public class SysProductType implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 品类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 显示顺序
     */
    @TableField(value = "order_num")
    private Integer orderNum;

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

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 图片
     */
    @TableField(exist = false)
    private List<String> imageUrls;

    @TableField(exist = false)
    private MultipartFile image;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}