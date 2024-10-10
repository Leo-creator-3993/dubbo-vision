package com.future.dubbo.provider.service;

import com.future.dubbo.provider.model.User;

public interface IUserService {

    User getUser();

    User getUserBy(String name, int age);
}
