package com.qq.common.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/26
 **/
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientConfig {

    private final EsConfig esConfig;

    /**
     * es客户端
     * 官方文档：https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.0/connecting.html
     * 官方实例：https://github.com/elastic/elasticsearch-java/
     *
     * @return
     */
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esConfig.getUsername(), esConfig.getPassword()));

        RestClientBuilder builder = RestClient.builder(
                        new HttpHost(esConfig.getHost(), esConfig.getPort()))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider));

        // 低级客户端
        RestClient restClient = builder.build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }
}
