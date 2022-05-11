package com.qq.product.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 商品图片
     */
    @TableField(exist = false)
    private List<String> imageUrls;

    /**
     * 新增时上传的图片
     */
    @TableField(exist = false)
    @JsonIgnore
    private MultipartFile[] images;

    /**
     * 品牌id
     */
    @TableField(exist = false)
    private Long brandId;

    /**
     * 品牌名称
     */
    @TableField(exist = false)
    private String brandName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}