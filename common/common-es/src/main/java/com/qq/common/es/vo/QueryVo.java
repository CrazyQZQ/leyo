package com.qq.common.es.vo;

import lombok.Data;

/**
 * @Description: 搜索条件
 * @Author QinQiang
 * @Date 2022/7/4
 **/
@Data
public class QueryVo {
    /**
     * 匹配字段
     */
    private String field;
    /**
     * 查询类型，0:match, 1:range
     */
    private String queryType;
    /**
     * 关键字，queryType=0时有效
     */
    private String keyword;
    /**
     * 开始条件，queryType=1时有效
     */
    private Object gte;
    /**
     * 结束条件，queryType=1时有效
     */
    private Object lte;
}
