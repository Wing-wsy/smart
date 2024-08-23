package com.yj.tech.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticSearchProperties {

	// 是否开启ES
	private Boolean open;

	// es 集群host ip 地址
	private String hosts;

	// es用户名
	private String userName;

	// es密码
	private String password;

	// es 请求方式
	private String scheme;

	// es集群名称
	private String clusterName;

	// es 连接超时时间
	private int connectTimeOut;

	// es socket 连接超时时间
	private int socketTimeOut;

	// es 请求超时时间
	private int connectionRequestTimeOut;

	// es 最大连接数
	private int maxConnectNum;

	// es 每个路由的最大连接数
	private int maxConnectNumPerRoute;

	// es api key
	private String apiKey;
	
}
