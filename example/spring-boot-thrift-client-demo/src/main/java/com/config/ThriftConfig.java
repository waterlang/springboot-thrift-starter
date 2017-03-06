package com.config;

//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.ILoadBalancer;
//import com.netflix.loadbalancer.IPing;
//import com.netflix.loadbalancer.Server;
//import com.netflix.loadbalancer.ServerList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
//import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
//import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Created by chenlang on 2016/9/20.
 */

@Configuration
@Slf4j
public class ThriftConfig {



    @Autowired(required = false)
    private DiscoveryClient discoveryClient;




    @Bean
    TTransport tTransport(){
//        List<ServiceInstance> list = discoveryClient.getInstances("thrift-server");
//        list.stream().forEach( p -> System.out.printf("serviceid:"+p.getServiceId()+",host :"+p.getHost()+",port:"+p.getPort()) );
        return  new TSocket("127.0.0.1",7911);
    }



}
