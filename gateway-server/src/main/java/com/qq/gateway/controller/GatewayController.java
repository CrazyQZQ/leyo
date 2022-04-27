package com.qq.gateway.controller;

import com.qq.gateway.config.MyConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/7
 **/
@RestController
@RequestMapping("/gateway")
@Data
public class GatewayController {
    @Autowired
    private MyConfig myConfig;
    /**
     * http://localhost:8080/config/get
     */
    @RequestMapping("/get")
    public String get() {
        return myConfig.toString();
    }
}
