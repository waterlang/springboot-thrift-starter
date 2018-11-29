package com.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenlang on 2017/3/2.
 */
@RestController
public class MyController {

    @RequestMapping("/hello")
    public String aa(){
        return "success";
    }
}
