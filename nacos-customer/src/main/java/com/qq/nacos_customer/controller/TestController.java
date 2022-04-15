package com.qq.nacos_customer.controller;

import com.qq.nacos_customer.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/2
 **/
@RestController
@RequestMapping("customer")
public class TestController {

//    @Value("${spring.datasource.dynamic.datasource.master.username}")
    @Value("${qq.name}")
    private String mysqlUsername;

    private final RestTemplate restTemplate;

    @Resource
    private TestService testService;

    @Autowired
    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/echo/{str}")
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-provider/provide/echo/" + str, String.class);
    }

    @GetMapping("/echo2/{str}")
    public String echo2(@PathVariable String str) {
        System.out.println("mysqlUsername:"+mysqlUsername);
        return testService.echo(str);
    }

    @GetMapping("test")
    public String test() {
        System.out.println("mysqlUsername:"+mysqlUsername);
        return mysqlUsername;
    }

    public String getMysqlUsername() {
        return mysqlUsername;
    }

    public void setMysqlUsername(String mysqlUsername) {
        this.mysqlUsername = mysqlUsername;
    }
}
