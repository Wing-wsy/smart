package com.yj.tech.es.admin.controller;

import com.yj.tech.es.admin.index.EsDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * spring-boot-starter-data-elasticsearch 使用
 */
@RestController
@RequestMapping("/demo/es")
public class EsDemoController {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     *  增
     */
    @PostMapping
    public void save(@RequestBody EsDemo esDemo) {
        // id字段必填
        esDemo.setCreateTime(LocalDateTime.now());
        elasticsearchOperations.save(esDemo);
    }

    /**
     *  删
     */
    @DeleteMapping("/{demoId}")
    public void delete(@PathVariable("demoId") Integer demoId) {
        elasticsearchOperations.delete(demoId.toString(), EsDemo.class);
    }

    /**
     *  改
     */
    @PutMapping
    public void update(@RequestBody EsDemo esDemo) {
        esDemo.setCreateTime(LocalDateTime.now());
        elasticsearchOperations.update(esDemo);
    }

    /**
     *  查
     */
    @GetMapping("/{demoId}")
    public EsDemo getInfo(@PathVariable("demoId") Integer demoId) {
        Criteria criteria = new Criteria("demoId").is(demoId);
        Query query = new CriteriaQuery(criteria);
        SearchHit<EsDemo> searchHit = elasticsearchOperations.searchOne(query, EsDemo.class);
        return Objects.isNull(searchHit) ? null : searchHit.getContent();
    }

    /**
     *  查集合
     */
    @GetMapping("/list")
    public List<EsDemo> list() {
        Criteria criteria = new Criteria();
        Query query = new CriteriaQuery(criteria);
        SearchHits<EsDemo> searchHits = elasticsearchOperations.search(query, EsDemo.class);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @GetMapping("/page")
    public Page<EsDemo> page(int pageNum, int pageSize) {
        Criteria criteria = new Criteria();
        PageRequest pageRequest = PageRequest.of(pageNum-1, pageSize);
        Query query = new CriteriaQuery(criteria).setPageable(pageRequest);
        SearchHits<EsDemo> searchHits = elasticsearchOperations.search(query, EsDemo.class);
        List<EsDemo> esDemos = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(esDemos, pageRequest, searchHits.getTotalHits());
    }

}
