package com.future.dubbo.consumer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -296205736260057316L;
    private String name;
    private int age;
}
