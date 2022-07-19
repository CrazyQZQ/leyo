package com.qq.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("用户登录信息")
public class LoginVO {
    @NotNull(message = "用户名不能为空！")
    @ApiModelProperty("用户名")
    private String username;
    @NotNull(message = "密码不能为空！")
    @ApiModelProperty("密码")
    private String password;
}
