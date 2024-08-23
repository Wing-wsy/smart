//package com.yj.tech.es.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 第一种配置方式：入门配置
// * @author wing
// * @create 2024/8/21
// */
//@Configuration
//public class ESConfig {
//
//    @Bean
//    public ElasticsearchClient elasticsearchClient() {
//        HttpHost host = new HttpHost("47.76.68.216", 9200, "http");
//        RestClientBuilder builder = RestClient.builder(host);
//        RestClient restClient = builder.build();
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        ElasticsearchClient client = new ElasticsearchClient(transport);
//        return client;
//    }
//
//}
