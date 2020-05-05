package ru.krasilova.otus.spring.brokerage.config;


import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class HystrixConfiguration {

    @Bean
    @Primary
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public HystrixCommandAspect hystrixAspect() {
        return new HystrixCommandAspect();
    }

}

