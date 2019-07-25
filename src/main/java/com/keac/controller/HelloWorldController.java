package com.keac.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@EnableAutoConfiguration
@RestController
public class HelloWorldController {
    @RequestMapping("/index")
    public String index(){
        return "success";
    }
    @RequestMapping("/getMap")
    public Map<String, Object> getMap(){
        Map<String , Object> result = new HashMap<String, Object>();
        result.put("errorCode", "200");
        result.put("errorMsg", "成功..");
        return result;
    }


}