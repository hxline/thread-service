package com.hxline.threadservice.register;

import com.hxline.threadservice.service.ThreadService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Handoyo
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@Import(ThreadService.class)
public class ThreadServer {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "thread-server");
        
	SpringApplication.run(ThreadServer.class, args);
    }
}

//class ServiceInstanceRestController {
//
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    public List<ServiceInstance> serviceInstancesByApplicationName(
//            @PathVariable String applicationName) {
//        return this.discoveryClient.getInstances(applicationName);
//    }
//}
