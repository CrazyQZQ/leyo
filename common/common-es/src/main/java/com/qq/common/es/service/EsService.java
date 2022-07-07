package com.qq.common.es.service;

import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.qq.common.es.vo.SearchCommonVO;
import com.qq.common.es.vo.SearchResultVO;

import java.io.IOException;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/26
 **/
public interface EsService {

    /**
     * 创建索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    boolean createIndex(String indexName) throws IOException;

    /**
     * 获取索引信息
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    GetIndexResponse getIndex(String indexName) throws IOException;

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    DeleteIndexResponse deleteIndex(String indexName) throws IOException;

    /**
     * 新增
     *
     * @param indexName
     * @param id
     * @param document
     * @return
     * @throws IOException
     */
    IndexResponse addDoc(String indexName, String id, Object document) throws IOException;

    /**
     * 修改
     *
     * @param indexName
     * @param id
     * @param document
     * @return
     * @throws IOException
     */
    UpdateResponse updateDoc(String indexName, String id, Object document) throws IOException;

    /**
     * 删除
     *
     * @param indexName
     * @param id
     * @return
     * @throws IOException
     */
    DeleteResponse deleteDoc(String indexName, String id) throws IOException;

    /**
     * 搜索
     *
     * @param searchCommonVO
     * @param clazz
     * @param <E>
     * @return
     */
    <E> SearchResultVO<E> search(SearchCommonVO searchCommonVO, Class<E> clazz);
}
