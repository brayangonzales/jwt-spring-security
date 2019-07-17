package com.example.demo.interfaceMapper;

import java.util.List;

import com.example.demo.model.Courses;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper{
    List<Courses> getCourses();
}