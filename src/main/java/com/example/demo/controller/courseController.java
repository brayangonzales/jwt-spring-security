package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.interfaceMapper.CourseMapper;
import com.example.demo.model.Courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class courseController {
    @Autowired
    CourseMapper courseMapper;

    @GetMapping("/")
    @ResponseBody
    public List<Courses> getList() {
        System.out.println("entro");
        return courseMapper.getCourses();
    }

    @GetMapping("/pruebaAdmin")
    @ResponseBody
    public List<String> getPruebaAdmin() {
        List<String> lista = new ArrayList<>();
        lista.add("a");
        lista.add("b");
        lista.add("c");
        lista.add("d");
        return lista;
    }
    @GetMapping("/programa")
    @ResponseBody
    public String getPrograma(){
        return "programa";
    }
}