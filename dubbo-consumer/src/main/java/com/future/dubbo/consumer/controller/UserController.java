package com.future.dubbo.consumer.controller;

import com.future.dubbo.consumer.dto.UserDto;
import com.future.dubbo.provider.model.User;
import com.future.dubbo.provider.service.IUserService;
import io.netty.util.internal.StringUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @DubboReference
    private IUserService iUserService;

    @GetMapping("/get")
    public User getUser() {
        RpcContext.getContext().setAttachment("dubbo.application", "#####Foo#####");
        return iUserService.getUser();
    }

    @PostMapping("getBy")
    public User getUserBy(@RequestBody UserDto userDto) {
        String userName = userDto.getName();
        int age = userDto.getAge();
        assert !StringUtil.isNullOrEmpty(userName) && age > 0;
        RpcContext.getContext().setAttachment("dubbo.application", "#####LeoLeo#####");
        return iUserService.getUserBy(userName, age);
    }
}