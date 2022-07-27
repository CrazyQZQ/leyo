package com.qq.common.es.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 搜索条件
 * @Author QinQiang
 * @Date 2022/7/4
 **/
@Data
@ApiModel("搜索条件")
public class QueryVo {
    /**
     * 匹配字段
     */
    @ApiModelProperty("匹配字段")
    private String field;
    /**
     * 查询类型，0:match, 1:range
     */
    @ApiModelProperty("查询类型，0:match, 1:range")
    private String queryType;
    /**
     * 关键字，queryType=0时有效
     */
    @ApiModelProperty("关键字，queryType=0时有效")
    private String keyword;
    /**
     * 开始条件，queryType=1时有效
     */
    @ApiModelProperty("开始条件，queryType=1时有效")
    private Object gte;
    /**
     * 结束条件，queryType=1时有效
     */
    @ApiModelProperty("结束条件，queryType=1时有效")
    private Object lte;
    /**
     * 格式化方式
     */
    @ApiModelProperty("格式化方式")
    private String format;
}
