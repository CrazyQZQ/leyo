package com.qq.common.es.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/10
 **/
@Data
@AllArgsConstructor
@ToString
@ApiModel("搜索参数")
public class SearchCommonVO {

    public SearchCommonVO() {
    }

    /**
     * 索引名
     */
    @ApiModelProperty("索引名")
    private String indexName;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Long fromMillis;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Long toMillis = System.currentTimeMillis();
    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer page;
    /**
     * 行数
     */
    @ApiModelProperty("行数")
    private Integer rows;
    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private String sortIndex;
    /**
     * 排序顺序
     */
    @ApiModelProperty("排序顺序")
    private String sortOrder;
    /**
     * 是否聚合
     */
    @ApiModelProperty("是否聚合")
    private Boolean aggregation = false;
    /**
     * 聚合区间
     */
    @ApiModelProperty("聚合区间")
    private String[] aggregationInterval = new String[]{};
    /**
     * 是否高亮
     */
    @ApiModelProperty("是否高亮")
    private Boolean highlight = false;
    /**
     * 高亮字段
     */
    @ApiModelProperty("高亮字段")
    private String[] highlightField = new String[]{};
    /**
     * 查询条件
     */
    @ApiModelProperty("查询条件")
    private List<QueryVo> queryVos;

    /**
     * 聚合
     */
    @ApiModelProperty("聚合")
    private AggVO agg;

    /**
     * 聚合条件
     */
    @Getter
    @Setter
    @Builder
    @ApiModel("聚合条件")
    public static class AggVO {
        /**
         * 聚合类型：terms,time,histogram
         */
        @ApiModelProperty("聚合类型：terms,time,histogram")
        private String type;
        /**
         * 聚合字段
         * 聚合类型为terms时必须为keyword类型字段
         */
        @ApiModelProperty("聚合字段")
        private String field;
        /**
         * bucket总数
         */
        @ApiModelProperty("bucket总数")
        private Integer size;
        /**
         * 间隔
         */
        @ApiModelProperty("间隔")
        private Double interval;
        /**
         * 度量函数
         */
        private String measureFun;
        /**
         * 度量字段
         */
        private String measureField;
        /**
         * 嵌套子聚合
         */
        @ApiModelProperty("嵌套子聚合")
        private AggVO childAgg;

    }
}
