package com.keac.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {
    @Value("${test}")
    private String test;
    @RequestMapping("/")
    @ResponseBody
    public String Index(){
        
    System.out.println(test);
        return "index";
    }
}
