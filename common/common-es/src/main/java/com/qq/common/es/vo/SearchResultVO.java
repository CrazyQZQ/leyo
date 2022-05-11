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
    private List<T> list;
    private long total;

    private boolean aggregation;
    private Map<String, List<AggregationVO>> aggregationMap;

    @Getter
    @Setter
    public static class AggregationVO {
        private String key;
        private long docCount;
    }
}
