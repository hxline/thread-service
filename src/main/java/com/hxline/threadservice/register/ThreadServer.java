package com.hxline.threadservice.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.threadservice.rest.ThreadRest;
import com.hxline.threadservice.domain.Thread;
import com.hxline.threadservice.messaging.consumer.ThreadConsumer;
import com.hxline.threadservice.services.interfaces.ThreadServicesInterface;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * 
 * @author Handoyo
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@Import(ThreadRest.class)
@ImportResource({"classpath*:thread-context.xml"})
public class ThreadServer {
    
    private static ThreadConsumer threadConsumer;

    public static void setThreadConsumer(ThreadConsumer threadConsumer) {
        ThreadServer.threadConsumer = threadConsumer;
    }
    
    public static void main(String[] args) throws IOException, TimeoutException {
        try {
            System.setProperty("spring.config.name", "thread-server");
            SpringApplication.run(ThreadServer.class, args);
            threadConsumer.consuming();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}