package com.yj.tech.redis;

import org.springframework.context.annotation.ComponentScan;

/**
 * 注册核心模块组件自动扫描机制
 *
 * @author fangen
 **/
@ComponentScan(basePackageClasses = RedisModule.class)
public class RedisModule {
}
