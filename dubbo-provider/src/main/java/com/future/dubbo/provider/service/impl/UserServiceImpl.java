package com.future.dubbo.provider.service.impl;

import com.future.dubbo.provider.model.User;
import com.future.dubbo.provider.service.IUserService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Random;

@DubboService
public class UserServiceImpl implements IUserService {

    @Override
    public User getUser() {
        return new User(10010, "DD", 200);
    }

    @Override
    public User getUserBy(String name, int age) {
        return new User(new Random().nextInt(), name, age);
    }
}