package com.easymall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.easymall.mapper")
public class SearchStarter {
    public static void main(String[] args) {
        SpringApplication.run(SearchStarter.class,args);
    }
}

