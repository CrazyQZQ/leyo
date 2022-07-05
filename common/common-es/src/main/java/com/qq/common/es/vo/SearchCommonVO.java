package com.qq.common.es.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/10
 **/
@Data
@AllArgsConstructor
@ToString
public class SearchCommonVO {
    public SearchCommonVO() {};
    /**
     * 索引名
     */
    private String indexName;
    /**
     * 开始时间
     */
    private long fromMillis;
    /**
     * 结束时间
     */
    private long toMillis = System.currentTimeMillis();
    /**
     * 页码
     */
    private int page = 1;
    /**
     * 行数
     */
    private int rows = 10;
    /**
     * 排序字段
     */
    private String sortIndex = "createTime";
    /**
     * 排序顺序
     */
    private String sortOrder = "Desc";
    /**
     * 是否聚合
     */
    private boolean aggregation = false;
    /**
     * 聚合类型
     */
    private String[] aggregationType = new String[] { "term" };
    /**
     * 聚合属性
     */
    private String[] aggregationField = new String[] {};
    /**
     * 聚合区间
     */
    private int[] aggregationTopN = new int[] { 10 };
    /**
     *  聚合区间
     */
    private String[] aggregationInterval = new String[] {};
    /**
     * 是否高亮
     */
    private boolean highlight = false;
    /**
     *  高亮属性
     */
    private String[] highlightField = new String[] {};
    /**
     * 查询条件
     */
    private List<QueryVo> queryVos;
}
