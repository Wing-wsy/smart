package com.yj.tech.es.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ESAdminApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ESAdminApplication.class, args);
        String[] names = run.getBeanDefinitionNames();
        int i = 1;
        /*for (String name : names) {
            System.out.println(i + "-加载："+ name);
            i++;
        }*/
    }

}
