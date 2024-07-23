package com.yj.tech.admin.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 常量类
 */
@Data
@Configuration
public class Constant {

    //生成的代码存放目录:resources下
    @Value("${filePlace}")
    public String filePlace;

    //vm模板存放目录
    @Value("${vmPlace}")
    public String vmPlace;

    public static String[] annos = {
            "/register",
            "/login",
            "/unAuth",
            "/user/refreshToken",
            ".css",
            "**.css",
            ".js",
            "**.js",
            "/doc.html",
            "/swagger-ui",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/favicon.ico",

            // 测试地址放行
            "/test/log",
            "/test/redisRock",
            "/test/redisRock1",
            "/test/redisRock2",

            // xxl-job 请求先不拦截，后续开发了页面需要拦截
            "/xxl-job/group",
            "/xxl-job/list",
            "/xxl-job/trigger",
            "/xxl-job/start",
            "/xxl-job/add",
            "/xxl-job/stop",
            "/xxl-job/remove",
            "/xxl-job/update",

    };

    public static List<String> annosList = Arrays.asList(annos);
}
