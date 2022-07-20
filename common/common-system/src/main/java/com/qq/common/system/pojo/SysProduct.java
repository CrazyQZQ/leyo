package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品表
 *
 * @TableName sys_product
 */
@TableName(value = "sys_product")
@Data
@ApiModel("商品基本信息")
public class SysProduct implements Serializable {
    /**
     * 商品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "商品ID", hidden = true)
    private Long id;

    /**
     * 商品编码
     */
    @TableField(value = "code")
    @ApiModelProperty("商品编码")
    private String code;

    /**
     * 商品名称
     */
    @TableField(value = "name")
    @ApiModelProperty("商品名称")
    private String name;

    /**
     * 单位
     */
    @TableField(value = "unit")
    @ApiModelProperty("单位")
    private String unit;

    /**
     * 价格
     */
    @TableField(value = "price")
    @ApiModelProperty("价格")
    private BigDecimal price;

    /**
     * 库存
     */
    @TableField(value = "stock")
    @ApiModelProperty("库存")
    private Integer stock;

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

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "修改人", hidden = true)
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date updateTime;

    /**
     * 商品图片
     */
    @TableField(exist = false)
    @ApiModelProperty("商品图片")
    private List<String> imageUrls;

    /**
     * 品牌id
     */
    @TableField(exist = false)
    @ApiModelProperty("品牌ID")
    private Long brandId;

    /**
     * 品牌名称
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private String brandName;

    /**
     * sku列表
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private List<SysSku> skus;

    /**
     * 属性
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private List<SysAttribute> attributes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}