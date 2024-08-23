package com.yj.tech.es.admin.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @GetMapping("/es")
    public void isFollow() {
        String indexName = "test_wing_777";
        ElasticsearchIndicesClient indices = elasticsearchClient.indices();
        ExistsRequest existsRequest = new ExistsRequest.Builder().index(indexName).build();

        try {
            boolean flag = indices.exists(existsRequest).value();
            if (flag) {
                System.out.println("已存在索引");
            } else {
                CreateIndexRequest request = new CreateIndexRequest.Builder().index(indexName).build();
                indices.create(request);
                System.out.println("创建索引成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
