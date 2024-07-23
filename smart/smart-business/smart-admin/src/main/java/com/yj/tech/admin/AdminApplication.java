package com.yj.tech.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.yj.tech.common.service","com.yj.tech.admin","com.yj.tech.redis"})
@MapperScan(basePackages = {"com.yj.tech.common.mapper"})
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AdminApplication.class, args);
        /*String[] names = run.getBeanDefinitionNames();
        int i = 1;
        for (String name : names) {
            System.out.println(i + "-加载："+ name);
            i++;
        }*/
    }

}
