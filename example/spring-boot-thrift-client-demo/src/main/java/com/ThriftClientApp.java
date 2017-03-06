package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by chenlang on 2016/9/20.
 */

@EnableEurekaClient
@SpringBootApplication
public class ThriftClientApp {


    public static void main(String[] args) {
        SpringApplication.run(ThriftClientApp.class,args);
    }

}
