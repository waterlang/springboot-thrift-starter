//package com.config;
//
//import com.service.UserService;
//import com.service.UserServiceImpl;
//import org.apache.thrift.TProcessor;
//import org.apache.thrift.protocol.TBinaryProtocol;
//import org.apache.thrift.protocol.TProtocolFactory;
//import org.apache.thrift.server.TServer;
//import org.apache.thrift.server.TThreadPoolServer;
//import org.apache.thrift.transport.TServerSocket;
//import org.apache.thrift.transport.TServerTransport;
//import org.apache.thrift.transport.TTransportException;
//import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * Created by chenlang on 2016/9/20.
// */
//
//@Configuration
//public class ThriftConfig {
//
//    ExecutorService executor = Executors.newSingleThreadExecutor();
//
//    @Bean
//    public TServerTransport tServerTransport() {
//        try {
//            return new TServerSocket(7911);
//        } catch (TTransportException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    @Bean
//
//    public TProtocolFactory tProtocolFactory() {
//        //We will use binary protocol, but it's possible to use JSON and few others as well
//        return new TBinaryProtocol.Factory();
//    }
//    @Bean
//    public TServer tServer() {
//        //发布服务
//        TProcessor processor = new UserService.Processor<UserService.Iface>(new UserServiceImpl());
//
//        TThreadPoolServer.Args arg = new TThreadPoolServer.Args(tServerTransport()).processor(processor);
//        arg.protocolFactory(tProtocolFactory());
//
//        TServer server = new TThreadPoolServer(arg);
////        server.serve();
//        executor.execute( new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Start server on port 7911...");
//                tServer().serve();
//            }
//        });
//        return server;
//    }
//
//
//}
