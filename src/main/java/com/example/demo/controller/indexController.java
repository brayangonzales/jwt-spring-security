package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class indexController{
    /*@RequestMapping(value="/",method=RequestMethod.GET)
    public String index(){
        return "index";
    }*/
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String app(){
        return "forward:/index.html";
    }
}