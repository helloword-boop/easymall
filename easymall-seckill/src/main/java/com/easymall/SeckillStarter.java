package com.easymall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.easymall.mapper")
public class SeckillStarter {
    public static void main(String[] args) {
        SpringApplication.run(SeckillStarter.class, args);
    }

    @Bean
    public Queue queue01() {
        return new Queue("seckillQueue");
    }

    @Bean
    public DirectExchange exchange01() {
        return new DirectExchange("seckillEx");
    }

    @Bean
    public Binding binding01() {
        return BindingBuilder.bind(queue01()).to(exchange01()).with("seckill");
    }
}
