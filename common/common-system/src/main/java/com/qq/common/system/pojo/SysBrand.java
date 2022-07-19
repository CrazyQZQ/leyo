package com.qq.common.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品品牌表
 *
 * @TableName sys_brand
 */
@TableName(value = "sys_brand")
@Data
@ApiModel("商品品牌基本信息")
public class SysBrand implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "品牌ID", hidden = true)
    private Long id;

    /**
     * 父id
     */
    @TableField(value = "parent_id")
    @ApiModelProperty("品牌父id")
    private Long parentId;

    /**
     * 品牌名称
     */
    @TableField(value = "name")
    @ApiModelProperty("品牌名称")
    private String name;

    /**
     * 品类id
     */
    @TableField(value = "type_id")
    @ApiModelProperty("品类id")
    private Long typeId;

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
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    /**
     * 图片
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "图片url", hidden = true)
    private List<String> imageUrls;

    @TableField(exist = false)
    @ApiModelProperty("图片")
    private MultipartFile image;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}