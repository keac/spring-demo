package com.keac.swagger.controller;


import com.keac.swagger.controller.testdo.TestDo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/test")
@Api("用户接口")
@RestController
public class DemoController {
    @GetMapping("/getUser")
    @ApiOperation(value = "获取用户信息",notes = "由微信端用")
    public String getUser(@ApiParam(value="用户名",required = false)  @RequestParam String username){

        return "success";
    }


    @PostMapping("/getUser")
    @ApiOperation("获取用户信息")
    public String getPostUser(@ApiParam("获取用户信息")@RequestBody TestDo testDto){

        return testDto.toString();
    }
}