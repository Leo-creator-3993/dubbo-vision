package com.future.dubbo.provider.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 7802591974697987743L;

    private int id;
    private String name;
    private int age;

    @Override
    public String toString(){
        return String.format("id:%s, name:%s, age:%s", id, name, age);
    }
}
