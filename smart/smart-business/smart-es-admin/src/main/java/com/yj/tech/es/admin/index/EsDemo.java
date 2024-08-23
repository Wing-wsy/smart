package com.yj.tech.es.admin.index;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * @author wing
 * @create 2024/8/23
 */
@Document(indexName = "demo")
@Data
public class EsDemo {

    @Id
    private Integer demoId;

    private String demoName;

    private LocalDateTime createTime;

}
