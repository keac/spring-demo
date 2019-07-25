package com.keac.controller;

import com.keac.mapper.UserMapper;
import com.keac.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@EnableAutoConfiguration
@RestController
public class HelloWorldController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    @ApiOperation(value = "登陆",notes = "由浏览器登陆")
    public String getUser(@RequestParam @ApiParam(value="用户名",required = true) String username, @RequestParam @ApiParam(value="密码",required = true) String password) {
        User user = new User();
        System.out.println(username);
        user.setName(username);
        user.setPassword(password);
        System.out.println(userMapper.selectByUser(user));
        if (userMapper.selectByUser(user) != null)
            return "Welcome "+user.getName();
        else
            return "Username or password error";
    }

    @GetMapping("/newUser")
    @ApiOperation(value = "注册",notes = "由浏览器注册")
    public String newUser(@RequestParam @ApiParam(value="用户名",required = true) String username, @RequestParam @ApiParam(value="密码",required = true) String password) {
        User user = new User();
        if(!(username.equals("")&&password.equals(""))){

        user.setName(username);
        user.setPassword(password);
        if (userMapper.selectByUser(user) == null)
            if (userMapper.insert(user) > 0)
                return "OK! please Login";
                return "Has this user!!!!!!!!!!!!!!!!!!!";
        }
            return "Some is Error~";
    }

    @RequestMapping("/getMap")
    public Map<String, Object> getMap(){
        Map<String , Object> result = new HashMap<String, Object>();
        result.put("errorCode", "200");
        result.put("errorMsg", "成功..");
        return result;
    }


}