package comqq.common.es.service;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;

import java.io.IOException;

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
}
