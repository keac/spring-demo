package com.keac.controller;


import com.keac.mappers.UserMapper;
import com.keac.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/login")
    @ApiOperation(value = "登陆", notes = "由浏览器登陆")
    @ApiResponses({
            @ApiResponse(code = 200, message = "登陆ok"),
            @ApiResponse(code = 301, message = "用户名或密码错误")
    })
    public Map<String, Object> getUser(@RequestParam @ApiParam(value = "用户名", required = true) String username, @RequestParam @ApiParam(value = "密码", required = true) String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        Map<String, Object> result = new HashMap<>();
        Example example=new Example(User.class);
        example.and();

        if (userMapper.select(user) != null) {
            stringRedisTemplate.opsForValue().set("username", username);
            result.put("errorCode", "200");
            result.put("errorMsg", "Welcome " + username);
            return result;
        } else {
            result.put("errorCode", "301");
            result.put("errorMsg", "Username or password error");
            return result;
        }

    }

    @GetMapping("/user")
    @ApiOperation(value = "用户信息", notes = "获取所有用户的信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 301, message = "没有权限访问")
    })
    private Map<String, Object> user() {
        Map<String, Object> result = new HashMap<>();
        if (stringRedisTemplate.opsForValue().get("username") != null) {

            String username = stringRedisTemplate.opsForValue().get("username");
            result.put("errorCode", "200");
            result.put("errorMsg", "User "+username);
            result.put("data", userMapper.selectAll());
            return result;
        }
        result.put("errorCode", "301");
        result.put("errorMsg", "Not login");
        return result;
    }

    @GetMapping("/newUser")
    @ApiOperation(value = "注册", notes = "注册新用户")
    @ApiResponses({
            @ApiResponse(code = 200, message = "注册成功"),
            @ApiResponse(code = 301, message = "错误")
    })
    public Map<String, Object> newUser(@RequestParam @ApiParam(value = "用户名", required = true) String username, @RequestParam @ApiParam(value = "密码", required = true) String password) {
        User user = new User();
        Map<String, Object> result = new HashMap<>();
        if (!(username.equals("") && password.equals(""))) {
            user.setName(username);
            user.setPassword(password);
            System.out.println(userMapper.select(user));
            if (userMapper.select(user).size()==0)
                if (userMapper.insert(user) > 0) {
                    result.put("errorCode", "200");
                    result.put("errorMsg", "ok");
                    return result;
                }
            result.put("errorCode", "301");
            result.put("errorMsg", "Has this user");
            return result;
        }
        result.put("errorCode", "301");
        result.put("errorMsg", "user or password is empty");
        return result;
    }
    @GetMapping("/delUser")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 500, message = "删除用户失败"),
            @ApiResponse(code = 300, message = "没有这个用户")
    })
    @ApiOperation(value = "删除用户", notes = "输入id删除用户")
    public Map<String, Object> delUser(@RequestParam @ApiParam(value = "用户id", required = true) int id) {
        Map<String, Object> result = new HashMap<>();
        if (stringRedisTemplate.opsForValue().get("username") != null) {
            if(userMapper.selectByPrimaryKey(id)==null){
                result.put("errorCode", "300");
                result.put("errorMsg", "No this user");
                return result;
            }
            if(userMapper.deleteByPrimaryKey(id)>0){
                result.put("errorCode", "200");
                result.put("errorMsg", "Del ok");
                return result;
            }else{
                result.put("errorCode", "500");
                result.put("errorMsg", "Del Error");
                return result;
            }

        }
        result.put("errorCode", "301");
        result.put("errorMsg", "Not login");
        return result;
    }

    @PostMapping("/updateUser")
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = 500, message = "更新用户失败"),
            @ApiResponse(code = 300, message = "没有这个用户")
    })
    @ApiOperation(value = "更新用户信息", notes = "输入用户id来更新")
    public Map<String, Object> updateUser(@RequestBody @ApiParam(required = true) User user) {
        Map<String, Object> result = new HashMap<>();
        if (stringRedisTemplate.opsForValue().get("username") != null) {
            if(userMapper.selectByPrimaryKey(user.getId())==null){
                result.put("errorCode", "300");
                result.put("errorMsg", "No this user");
                return result;
            }
            if(userMapper.updateByPrimaryKeySelective(user)>0){
                result.put("errorCode", "200");
                result.put("errorMsg", "Update ok");
                return result;
            }else{
                result.put("errorCode", "500");
                result.put("errorMsg", "Del Error");
                return result;
            }

        }
        result.put("errorCode", "301");
        result.put("errorMsg", "Not login");
        return result;
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出登陆", notes = "退出登陆")
    public Map<String, Object> logout() {
        Map<String, Object> result = new HashMap<>();
        stringRedisTemplate.delete("username");
        result.put("errorCode", "200");
        result.put("errorMsg", "成功..");
        return result;
    }


}