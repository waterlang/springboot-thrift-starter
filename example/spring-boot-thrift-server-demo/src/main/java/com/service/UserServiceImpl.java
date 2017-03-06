package com.service;

import org.apache.thrift.TException;

/**
 * Created by chenlang on 2016/9/20.
 */

@lyqf.spring.thrift.ThriftService
public class UserServiceImpl implements UserService.Iface {

    @Override
    public UserDto getUser() throws TException {

        UserDto userDto = new UserDto();
        userDto.setId(3123);
        userDto.setUsername("waterlang");
        return userDto;
    }
}
