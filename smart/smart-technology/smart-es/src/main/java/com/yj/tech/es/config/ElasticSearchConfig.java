package com.yj.tech.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 第二种配置方式：基本配置
 * @author wing
 * @create 2024/8/23
 */
@Data
@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Value("${spring.elasticsearch.uris}")
    private List<String> uris;

    public RestClientBuilder creatBaseConfBuilder(ElasticSearchProperties es){

        // 1. 单节点ES Host获取
        String host = es.getHosts().split(":")[0];
        String port = es.getHosts().split(":")[1];
        HttpHost httpHost = new HttpHost(host, Integer.parseInt(port),(es.getScheme() == null) ? "http" : es.getScheme());

        // 2. 创建构建器对象
        //RestClientBuilder: ES客户端库的构建器接口,用于构建RestClient实例;允许你配置与Elasticsearch集群的连接,设置请求超时,设置身份验证,配置代理等
        RestClientBuilder builder = RestClient.builder(httpHost);

        // 连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(es.getConnectTimeOut());
            requestConfigBuilder.setSocketTimeout(es.getSocketTimeOut());
            requestConfigBuilder.setConnectionRequestTimeout(es.getConnectionRequestTimeOut());
            return requestConfigBuilder;
        });

        // 3. HttpClient 连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(es.getMaxConnectNum());
            httpClientBuilder.setMaxConnPerRoute(es.getMaxConnectNumPerRoute());
            return httpClientBuilder;
        });

        return builder;
    }

    @Bean
    public ElasticsearchClient directConnectionESClient(ElasticSearchProperties es){
        // scheme 默认 http
        RestClientBuilder builder = creatBaseConfBuilder(es);
        ElasticsearchTransport transport = new RestClientTransport(builder.build(), new JacksonJsonpMapper());
        ElasticsearchClient esClient = new ElasticsearchClient(transport);
        return esClient;
    };

    @Override
    public ClientConfiguration clientConfiguration() {
        // 去除http开头
        uris = uris.stream().map(uri -> {
            if (uri.startsWith("http")) {
                uri = uri.split("://")[1];
            }
            return uri;
        }).toList();
        return ClientConfiguration.builder()
                .connectedTo(uris.toArray(new String[uris.size()]))
                .build();
    }

    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        // 注入自定义转换器处理LocalDateTime数据
        List<Converter<?, ?>> converters = new ArrayList<>(16);
        converters.add(StringToLocalDateTimeConverter.INSTANCE);
        converters.add(LocalDateTimeToStringConverter.INSTANCE);
        return new ElasticsearchCustomConversions(converters);
    }

    /**
     * LocalDateTime转String
     */
    @WritingConverter
    private enum LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
        /**
         * 实例化
         */
        INSTANCE;

        @Override
        public String convert(LocalDateTime source) {
            return source.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
        }
    }

    /**
     * String转LocalDateTime
     */
    @ReadingConverter
    private enum StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
        /**
         * 实例化
         */
        INSTANCE;

        @Override
        public LocalDateTime convert(String source) {
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
        }
    }

}
