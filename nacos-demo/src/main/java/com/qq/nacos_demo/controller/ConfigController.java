package com.qq.nacos_demo.controller;

import com.qq.nacos_demo.nacosconfig.MyConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/2
 **/
@RestController
@RequestMapping("/config")
@Data
public class ConfigController {

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
