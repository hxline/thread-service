package com.hxline.threadservice.register;

import com.hxline.threadservice.rest.ThreadRest;
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
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "thread-server");
        
	SpringApplication.run(ThreadServer.class, args);
    }
}