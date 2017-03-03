package com.hxline.threadservice.config;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Handoyo
 */
@Configuration
public class HystrixConfiguration {

  @Bean
  public HystrixCommandAspect hystrixAspect() {
    return new HystrixCommandAspect();
  }
}