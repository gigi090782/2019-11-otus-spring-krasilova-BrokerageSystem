package ru.krasilova.otus.spring.brokerage;

import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;



@EnableCircuitBreaker
@EnableHystrixDashboard
@SpringBootApplication
public class BrokerageApplication {


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BrokerageApplication.class);

    }

}
