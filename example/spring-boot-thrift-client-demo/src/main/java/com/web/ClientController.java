package com.web;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by chenlang on 2016/9/20.
 */
@RestController
public class ClientController {

    @Autowired
    TTransport transport;



    @RequestMapping("/aa")
    public  UserDto aa(){
        UserDto u = null;
        try {
            transport.open();
            //设置传输协议为 TBinaryProtocol
            TProtocol protocol = new TBinaryProtocol(transport);
            UserService.Client client =  new UserService.Client(protocol);
            u = client.getUser();
            System.out.println("------------"+u);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  u;

    }


}
