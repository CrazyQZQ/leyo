package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("商品品类基本信息")
public class SysProductType implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "品类ID", hidden = true)
    private Long id;

    /**
     * 父id
     */
    @TableField(value = "parent_id")
    @ApiModelProperty("父id")
    private Long parentId;

    /**
     * 品类名称
     */
    @TableField(value = "name")
    @ApiModelProperty("品类名称")
    private String name;

    /**
     * 显示顺序
     */
    @TableField(value = "order_num")
    @ApiModelProperty("显示顺序")
    private Integer orderNum;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新者", hidden = true)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    /**
     * 图片
     */
    @TableField(exist = false)
    @ApiModelProperty("图片url")
    private List<String> imageUrls;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}