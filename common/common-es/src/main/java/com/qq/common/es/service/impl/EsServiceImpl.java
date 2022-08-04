package com.qq.common.es.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.JsonData;
import com.alibaba.fastjson.JSON;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.utils.StringUtils;
import com.qq.common.es.service.EsService;
import com.qq.common.es.vo.QueryVo;
import com.qq.common.es.vo.SearchCommonVO;
import com.qq.common.es.vo.SearchCommonVO.AggVO;
import com.qq.common.es.vo.SearchResultVO;
import com.qq.common.es.vo.SearchResultVO.AggregationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: es操作实现类
 * @Author QinQiang
 * @Date 2022/4/26
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class EsServiceImpl implements EsService {

    final ElasticsearchClient esClient;

    /**
     * 创建索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    @Override
    public boolean createIndex(String indexName) throws IOException {
        //创建索引并返回状态
        return esClient.indices().create(c -> c.index(indexName)).acknowledged();
    }

    /**
     * 获取索引信息
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    @Override
    public GetIndexResponse getIndex(String indexName) throws IOException {
        return esClient.indices().get(c -> c.index(indexName));
    }

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    @Override
    public DeleteIndexResponse deleteIndex(String indexName) throws IOException {
        return esClient.indices().delete(c -> c.index(indexName));
    }

    /**
     * 新增
     *
     * @param indexName
     * @param id
     * @param document
     * @return
     * @throws IOException
     */
    @Override
    public IndexResponse addDoc(String indexName, String id, Object document) throws IOException {
        log.info("addDoc --> index:{}, id:{}, document:{}", indexName, id, JSON.toJSONString(document));
        IndexResponse indexResponse = esClient.index(IndexRequest.of(x -> {
            x.id(id);
            x.index(indexName);
            x.document(document);
            return x;
        }));
        return indexResponse;
    }

    /**
     * 修改
     *
     * @param indexName
     * @param id
     * @param document
     * @return
     * @throws IOException
     */
    @Override
    public UpdateResponse updateDoc(String indexName, String id, Object document) throws IOException {
        log.info("updateDoc --> index:{}, id:{}, document:{}", indexName, id, JSON.toJSONString(document));
        UpdateRequest<Object, Object> req = UpdateRequest.of(x -> x.index(indexName).id(id).doc(document));
        UpdateResponse<Object> update = esClient.update(req, Object.class);
        return update;
    }

    /**
     * 删除
     *
     * @param indexName
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public DeleteResponse deleteDoc(String indexName, String id) throws IOException {
        log.info("deleteDoc --> index:{}, id:{}", indexName, id);
        DeleteResponse deleteResponse = esClient.delete(c -> c.index(indexName).id(id));
        return deleteResponse;
    }

    /**
     * 删除索引数据
     *
     * @param searchCommonVO
     * @return
     * @throws IOException
     */
    @Override
    public DeleteByQueryResponse deleteByQuery(SearchCommonVO searchCommonVO) throws IOException {
        log.info("deleteByQuery --> searchCommonVO:{}", JSON.toJSONString(searchCommonVO));
        if (StringUtils.isEmpty(searchCommonVO.getIndexName())) {
            throw new ServiceException("索引不能为空!");
        }
        List<QueryVo> queryVos = searchCommonVO.getQueryVos();
        if (CollUtil.isEmpty(queryVos)) {
            throw new ServiceException("查询条件不能为空!");
        }
        // 处理查询条件
        List<Query> queries = processQuery(queryVos);
        // DeleteByQueryRequest of = DeleteByQueryRequest.of(d -> d.index(searchCommonVO.getIndexName())
        //         .query(q -> q.range(r -> r.field("@timestamp").lte(JsonData.of("now-30m")).format("epoch_millis"))));
        DeleteByQueryRequest request = DeleteByQueryRequest.of(d -> d.index(searchCommonVO.getIndexName())
                .query(q -> q.bool(b -> b.must(queries))));
        DeleteByQueryResponse deleteByQueryResponse = esClient.deleteByQuery(request);
        return deleteByQueryResponse;
    }

    /**
     * 搜索
     *
     * @param searchCommonVO
     * @param clazz
     * @param <E>
     * @return
     */
    @Override
    public <E> SearchResultVO<E> search(SearchCommonVO searchCommonVO, Class<E> clazz) {
        log.info("search --> searchCommonVO:{}", JSON.toJSONString(searchCommonVO));
        if (StringUtils.isEmpty(searchCommonVO.getIndexName())) {
            throw new ServiceException("索引不能为空!");
        }
        List<QueryVo> queryVos = searchCommonVO.getQueryVos();
        // 处理查询条件
        List<Query> queries = processQuery(queryVos);
        try {
            Map<String, Aggregation> stringAggregationMap = searchCommonVO.getAggregation().booleanValue() && ObjectUtil.isNotEmpty(searchCommonVO.getAgg()) ?
                    parseAggregation(searchCommonVO.getAgg()) : Collections.emptyMap();
            SearchRequest req = SearchRequest.of(sr -> {
                        sr.index(searchCommonVO.getIndexName())
                                .trackTotalHits(h -> h.enabled(Boolean.TRUE))
                                .size(searchCommonVO.getRows());
                        // 分页参数
                        if (searchCommonVO.getRows() != null && searchCommonVO.getPage() != null && searchCommonVO.getRows() > 0 && searchCommonVO.getPage() > 0) {
                            sr.from(searchCommonVO.getRows() * (searchCommonVO.getPage() - 1));
                        }
                        // 排序
                        if (StrUtil.isNotEmpty(searchCommonVO.getSortIndex()) && StrUtil.isNotEmpty(searchCommonVO.getSortOrder())) {
                            sr.sort(s -> s.field(FieldSort.of(fs -> fs.field(searchCommonVO.getSortIndex()).order(SortOrder.valueOf(searchCommonVO.getSortOrder())))));
                        }
                        // 查询条件
                        if (CollUtil.isNotEmpty(queries)) {
                            sr.query(q -> q.bool(b -> b.must(queries)));
                        }
                        // 高亮
                        if (searchCommonVO.getHighlight().booleanValue() && searchCommonVO.getHighlightField().length > 0) {
                            sr.highlight(h -> {
                                for (int idx = 0; idx < searchCommonVO.getHighlightField().length; ++idx) {
                                    final String field = searchCommonVO.getHighlightField()[idx];
                                    h.fields(field, HighlightField.of(hf -> hf
                                            .preTags("<font style='font-weight:600;background-color: #e75213;color: yellow;'>")
                                            .postTags("</font>"))).fragmentSize(1024);
                                }
                                return h;
                            });
                        }
                        // 聚合
                        sr.aggregations(stringAggregationMap);
                        return sr;
                    }
            );
            SearchResponse<E> searchResponse = esClient.search(req, clazz);
            return parseResponse(searchResponse);
        } catch (Exception e) {
            log.error("es查询出错", e);
        }
        return null;
    }

    /**
     * 处理查询条件
     *
     * @param queryVos
     */
    private List<Query> processQuery(List<QueryVo> queryVos) {
        if(CollUtil.isEmpty(queryVos)){
            return Collections.emptyList();
        }
        List<Query> queries = new ArrayList<>();
        for (QueryVo queryVo : queryVos) {
            if ("0".equals(queryVo.getQueryType())) {
                if (StrUtil.isEmpty(queryVo.getKeyword()))
                    continue;
                Query query = MatchQuery.of(m -> m.field(queryVo.getField()).query(queryVo.getKeyword()))._toQuery();
                queries.add(query);
            } else if ("1".equals(queryVo.getQueryType())) {
                if (StrUtil.isEmpty(queryVo.getField()))
                    continue;
                Query query = RangeQuery.of(r -> {
                    r.field(queryVo.getField());
                    if (ObjectUtil.isNotEmpty(queryVo.getGte())) {
                        r.gte(JsonData.of(queryVo.getGte()));
                    }
                    if (ObjectUtil.isNotEmpty(queryVo.getLte())) {
                        r.lte(JsonData.of(queryVo.getLte()));
                    }
                    if (StrUtil.isNotEmpty(queryVo.getFormat())) {
                        r.format(queryVo.getFormat());
                    }
                    return r;
                })._toQuery();
                queries.add(query);
            }
        }
        return queries;
    }

    /**
     * 处理聚合参数
     *
     * @param agg
     * @return
     */
    private Map<String, Aggregation> parseAggregation(AggVO agg) {
        Map<String, Aggregation> aggregationMap = new HashMap<>();
        String aggType = agg.getType();
        String aggField = agg.getField();
        Integer aggSize = agg.getSize();
        Double aggInterval = agg.getInterval();
        Aggregation aggregation = null;
        Map<String, Aggregation> childAggregationMap = null;
        if (ObjectUtil.isNotEmpty(agg.getChildAgg())) {
            childAggregationMap = parseAggregation(agg.getChildAgg());
        }
        switch (aggType) {
            case "terms":
                Map<String, Aggregation> finalTermsChildAggregationMap = childAggregationMap;
                aggregation = Aggregation.of(a -> {
                    Aggregation.Builder.ContainerBuilder terms = a.terms(t -> t.field(aggField).size(aggSize));
                    if (MapUtil.isNotEmpty(finalTermsChildAggregationMap)) {
                        terms.aggregations(finalTermsChildAggregationMap);
                    }
                    return terms;
                });
                break;
            case "time":
                Map<String, Aggregation> finalTimeChildAggregationMap = childAggregationMap;
                aggregation = Aggregation.of(a -> {
                    Aggregation.Builder.ContainerBuilder dateHistogram = a.dateHistogram(d -> d.field(aggField).calendarInterval(CalendarInterval.Day));
                    if (MapUtil.isNotEmpty(finalTimeChildAggregationMap)) {
                        dateHistogram.aggregations(finalTimeChildAggregationMap);
                    }
                    return dateHistogram;
                });
                break;
            case "histogram":
                Map<String, Aggregation> finalHistogramChildAggregationMap = childAggregationMap;
                aggregation = Aggregation.of(a -> {
                    Aggregation.Builder.ContainerBuilder histogram = a.histogram(d -> d.field(aggField).interval(aggInterval));
                    if (MapUtil.isNotEmpty(finalHistogramChildAggregationMap)) {
                        histogram.aggregations(finalHistogramChildAggregationMap);
                    }
                    return histogram;
                });
                break;
            default:
                break;
        }
        if (ObjectUtil.isEmpty(aggregation)) {
            throw new ServiceException("聚合类型错误！");
        }
        String measureField = agg.getMeasureField();
        String measureFun = agg.getMeasureFun();
        Aggregation measureAggregation = null;
        if (StrUtil.isNotEmpty(measureField) && StrUtil.isNotEmpty(measureFun)) {
            switch (measureFun) {
                case "max":
                    measureAggregation = Aggregation.of(a -> a.max(b -> b.field(measureField)));
                    break;
                case "avg":
                    measureAggregation = Aggregation.of(a -> a.avg(b -> b.field(measureField)));
                    break;
                case "min":
                    measureAggregation = Aggregation.of(a -> a.min(b -> b.field(measureField)));
                    break;
                case "sum":
                    measureAggregation = Aggregation.of(a -> a.sum(b -> b.field(measureField)));
                    break;
                default:
                    break;
            }
        }
        if (ObjectUtil.isNotEmpty(measureAggregation)) {
            aggregationMap.put(String.format("%s_%s", measureField, measureFun), measureAggregation);
        }
        aggregationMap.put(String.format("%s_%s_%d", aggField, aggType, aggSize), aggregation);
        return aggregationMap;
    }

    /**
     * 处理返回值
     *
     * @param res
     * @param <E>
     * @return
     */
    private <E> SearchResultVO<E> parseResponse(SearchResponse<E> res) {
        List<E> list = new ArrayList<>();
        res.hits().hits().forEach(h -> {
            E source = h.source();
            if (source != null) {
                for (String highlightKey : h.highlight().keySet()) {
                    StringBuilder highlightedVal = new StringBuilder();
                    h.highlight().get(highlightKey).forEach(highlightedVal::append);
                    // 设置属性值
                    BeanUtil.setFieldValue(source, highlightKey, highlightedVal);
                }
                list.add(source);
            }
        });
        SearchResultVO<E> searchResultVO = new SearchResultVO<>();
        searchResultVO.setList(list);
        searchResultVO.setTotal(res.hits().total().value());
        Map<String, Aggregate> aggregateMap = res.aggregations();
        if (MapUtil.isNotEmpty(aggregateMap)) {
            searchResultVO.setAggregation(true);
            searchResultVO.setAggregationMap(parseAggregationResponse(aggregateMap));
        }
        return searchResultVO;
    }

    /**
     * 解析聚合返回值
     * todo 其他类型解析
     * @param aggregations
     * @return
     */
    private Map<String, List<AggregationVO>> parseAggregationResponse(Map<String, Aggregate> aggregations) {
        if (MapUtil.isEmpty(aggregations)) {
            return null;
        }
        Map<String, List<AggregationVO>> aggregationMap = new HashMap<>();
        for (Map.Entry<String, Aggregate> entry : aggregations.entrySet()) {
            final Aggregate aggregate = entry.getValue();
            List<AggregationVO> aggregationVOList = new ArrayList<>();
            if (aggregate.isSterms()) {
                final StringTermsAggregate stringTermsAggregate = aggregate.sterms();
                stringTermsAggregate.buckets().array().forEach(b -> {
                    AggregationVO aggregationVO = new AggregationVO();
                    Map<String, Aggregate> childAggregate = b.aggregations();
                    if(MapUtil.isNotEmpty(childAggregate)){
                        Map<String, List<AggregationVO>> childAggMap = parseAggregationResponse(childAggregate);
                        aggregationVO.setChildAggMap(childAggMap);
                    }
                    aggregationVO.setKey(b.key());
                    aggregationVO.setDocCount(b.docCount());
                    aggregationVOList.add(aggregationVO);
                });
            } else if (aggregate.isDateHistogram()) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final DateHistogramAggregate dateHistogramAggregate = aggregate.dateHistogram();
                dateHistogramAggregate.buckets().array().forEach(b -> {
                    AggregationVO aggregationVO = new AggregationVO();
                    Map<String, Aggregate> childAggregate = b.aggregations();
                    if(MapUtil.isNotEmpty(childAggregate)){
                        Map<String, List<AggregationVO>> childAggMap = parseAggregationResponse(childAggregate);
                        aggregationVO.setChildAggMap(childAggMap);
                    }
                    aggregationVO.setKey(sdf.format(b.key().toEpochMilli()));
                    aggregationVO.setDocCount(b.docCount());
                    aggregationVOList.add(aggregationVO);
                });
            } else if (aggregate.isHistogram()) {
                final HistogramAggregate histogramAggregate = aggregate.histogram();
                histogramAggregate.buckets().array().forEach(b -> {
                    AggregationVO aggregationVO = new AggregationVO();
                    Map<String, Aggregate> childAggregate = b.aggregations();
                    if(MapUtil.isNotEmpty(childAggregate)){
                        Map<String, List<AggregationVO>> childAggMap = parseAggregationResponse(childAggregate);
                        aggregationVO.setChildAggMap(childAggMap);
                    }
                    aggregationVO.setKey(StrUtil.toString(b.key()));
                    aggregationVO.setDocCount(b.docCount());
                    aggregationVOList.add(aggregationVO);
                });
            } else if (aggregate.isMax()) {
                MaxAggregate max = aggregate.max();
                double value = max.value();
                AggregationVO aggregationVO = new AggregationVO();
                aggregationVO.setMeasureValue(value);
                aggregationVOList.add(aggregationVO);
            } else if (aggregate.isMin()) {
                MinAggregate min = aggregate.min();
                double value = min.value();
                AggregationVO aggregationVO = new AggregationVO();
                aggregationVO.setMeasureValue(value);
                aggregationVOList.add(aggregationVO);
            } else if (aggregate.isAvg()) {
                AvgAggregate avg = aggregate.avg();
                double value = avg.value();
                AggregationVO aggregationVO = new AggregationVO();
                aggregationVO.setMeasureValue(value);
                aggregationVOList.add(aggregationVO);
            }
            aggregationMap.put(entry.getKey(), aggregationVOList);
        }
        return aggregationMap;
    }
}
