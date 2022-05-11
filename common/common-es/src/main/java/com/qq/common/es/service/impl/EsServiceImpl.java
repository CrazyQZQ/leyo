package com.qq.common.es.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.JsonData;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.utils.StringUtils;
import com.qq.common.es.service.EsService;
import com.qq.common.es.vo.SearchCommonVO;
import com.qq.common.es.vo.SearchResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/26
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class EsServiceImpl implements EsService {

    final ElasticsearchClient esClient;

    @Override
    public boolean createIndex(String indexName) throws IOException {
        //创建索引并返回状态
        return esClient.indices().create(c -> c.index(indexName)).acknowledged();
    }

    @Override
    public GetIndexResponse getIndex(String indexName) throws IOException {
        return esClient.indices().get(c -> c.index(indexName));
    }

    @Override
    public DeleteIndexResponse deleteIndex(String indexName) throws IOException {
        return esClient.indices().delete(c -> c.index(indexName));
    }

    @Override
    public IndexResponse addDoc(String indexName, String id, Object document) throws IOException {
        IndexResponse indexResponse = esClient.index(IndexRequest.of(x -> {
            x.id(id);
            x.index(indexName);
            x.document(document);
            return x;
        }));
        return indexResponse;
    }

    @Override
    public UpdateResponse updateDoc(String indexName, String id, Object document) throws IOException {
        UpdateRequest<Object, Object> req = UpdateRequest.of(x -> x.index(indexName).id(id).doc(document));
        UpdateResponse<Object> update = esClient.update(req, Object.class);
        return update;
    }

    @Override
    public DeleteResponse deleteDoc(String indexName, String id) throws IOException {
        DeleteResponse deleteResponse = esClient.delete(c -> c.index(indexName).id(id));
        return deleteResponse;
    }

    @Override
    public SearchResultVO<Map> search(SearchCommonVO searchCommonVO) {
        if (StringUtils.isEmpty(searchCommonVO.getIndexName())) {
            throw new ServiceException("索引不能为空!");
        }
        if (StringUtils.isEmpty(searchCommonVO.getSearchKeyword())) {
            throw new ServiceException("查询关键字不能为空!");
        }
        try {
            SearchRequest req = SearchRequest.of(sr -> sr
                    .trackTotalHits(h -> h
                            .enabled(Boolean.TRUE)
                    )
                    .size(searchCommonVO.getRows())
                    .from(searchCommonVO.getRows() * (searchCommonVO.getPage() - 1))
                    .index(searchCommonVO.getIndexName())
                    // .sort(st -> st
                    //         .field(
                    //                 FieldSort.of(f -> f
                    //                         .field(searchCommonVO.getSortIndex())
                    //                         .order(SortOrder.valueOf(searchCommonVO.getSortOrder()))
                    //                 )
                    //         )
                    // )
                    .query(q -> q
                            .bool(b -> b
                                    // .must(m -> m
                                    //         .range(r -> r
                                    //                 .field(searchCommonVO.getSortIndex())
                                    //                 .gte(JsonData.of(searchCommonVO.getFromMillis()))
                                    //                 .lte(JsonData.of(searchCommonVO.getToMillis()))
                                    //         )
                                    // )
                                    .must(m -> m.queryString(qs -> qs.query(searchCommonVO.getSearchKeyword())))
                            )
                    )
                    .highlight(h -> {
                        if (searchCommonVO.isHighlight()) {
                            for (int idx = 0; idx < searchCommonVO.getHighlightField().length; ++idx) {
                                final String field = searchCommonVO.getHighlightField()[idx];
                                h.fields(field, HighlightField.of(hf -> hf
                                                .preTags("<font color='#e75213'>")
                                                .postTags("</font>")
                                                .highlightQuery(hq -> hq
                                                        .term(tq -> tq
                                                                .field(field)
                                                                .value(searchCommonVO.getSearchKeyword())
                                                        )
                                                )
                                        ))
                                        .fragmentSize(1024);
                            }
                        }
                        return h;
                    })
                    .aggregations(parseAggregation(searchCommonVO))
            );
            SearchResponse<Map> searchResponse = esClient.search(req, Map.class);
            return parseResponse(searchResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Aggregation> parseAggregation(SearchCommonVO searchCommonVO) {
        return new HashMap<>();
    }

    private SearchResultVO<Map> parseResponse(SearchResponse<Map> res) {
        List<Map> list = new ArrayList<>();
        res.hits().hits().forEach(h -> {
            Map source = h.source();
            if(source != null) {
                for(String highlightKey : h.highlight().keySet()) {
                    StringBuilder highlightedVal = new StringBuilder();
                    h.highlight().get(highlightKey).forEach(highlightedVal::append);
                    source.put(highlightKey,highlightedVal.toString());
                }
                list.add(source);
            }
        });

        SearchResultVO<Map> searchResultVO = new SearchResultVO<Map>();
        searchResultVO.setList(list);
        searchResultVO.setTotal(res.hits().total().value());
        return searchResultVO;
    }
}
