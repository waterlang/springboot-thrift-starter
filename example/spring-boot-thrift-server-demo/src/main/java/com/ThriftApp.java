package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by chenlang on 2016/9/20.
 */

@EnableEurekaClient
//@EnableDiscoveryClient
@SpringBootApplication
public class ThriftApp {

    public static void main(String[] args) {
        SpringApplication.run(ThriftApp.class,args);
    }
}
