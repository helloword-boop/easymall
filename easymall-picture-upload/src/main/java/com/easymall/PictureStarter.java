package com.easymall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PictureStarter {
    public static void main(String[] args) {
        SpringApplication.run(PictureStarter.class,args);
    }
}
