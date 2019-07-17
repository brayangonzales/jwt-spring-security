package com.example.demo.interfaceMapper;

import java.util.List;

import com.example.demo.model.Role;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
    List<Role> getRoles();
}