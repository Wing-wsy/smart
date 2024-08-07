package com.yj.tech.file.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.yj.tech.file"})
@SpringBootApplication
public class FileAdminApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(FileAdminApplication.class, args);
        String[] names = run.getBeanDefinitionNames();
        int i = 1;
        /*for (String name : names) {
            System.out.println(i + "-加载："+ name);
            i++;
        }*/
    }

}
