package com.keac.swagger.controller.testdo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("测试样例")
public class TestDo {

    @ApiModelProperty(value = "账号", required = true)
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
