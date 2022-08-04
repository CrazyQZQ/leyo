package com.qq.common.es.vo;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @Description: es查询结果VO
 * @Author QinQiang
 * @Date 2022/5/11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultVO<T> {
    /**
     * 查询结果list
     */
    private List<T> list;
    /**
     * 查询结果总数
     */
    private long total;

    /**
     * 是否聚合
     */
    private boolean aggregation;
    /**
     * 聚合结果map
     */
    private Map<String, List<AggregationVO>> aggregationMap;

    @Getter
    @Setter
    public static class AggregationVO {
        /**
         * 分组key
         */
        private String key;
        /**
         * 文档数量
         */
        private Long docCount;
        /**
         * 度量函数计算值
         */
        private Object measureValue;
        /**
         * 子聚合
         */
        Map<String, List<AggregationVO>> childAggMap;
    }
}
