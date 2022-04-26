package com.qq.es.server.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.qq.es.server.pojo.User;
import com.qq.es.server.service.EsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/26
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
}
