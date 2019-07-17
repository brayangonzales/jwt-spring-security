package com.example.demo.interfaceMapper;

import com.example.demo.model.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User getUser(String usuario);
}