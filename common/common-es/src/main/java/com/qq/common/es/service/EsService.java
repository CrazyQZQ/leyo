package com.qq.common.es.service;

import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.qq.common.es.vo.SearchCommonVO;
import com.qq.common.es.vo.SearchResultVO;

import java.io.IOException;
import java.util.Map;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/26
 **/
public interface EsService {

    boolean createIndex(String indexName) throws IOException;

    GetIndexResponse getIndex(String indexName) throws IOException;

    DeleteIndexResponse deleteIndex(String indexName) throws IOException;

    IndexResponse addDoc(String indexName, String id, Object document) throws IOException;

    UpdateResponse updateDoc(String indexName, String id, Object document) throws IOException;

    DeleteResponse deleteDoc(String indexName, String id) throws IOException;

    SearchResultVO<Map> search(SearchCommonVO searchCommonVO);
}
