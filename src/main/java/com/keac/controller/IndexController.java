package com.keac.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/IndexController")
    public String IndexController(){
        int i=1/0;
        System.out.println(i);
        return "indexcontroller";
    }
}
