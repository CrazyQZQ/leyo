package com.qq.account;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.qq.account", "com.qq.common.system"}, exclude = DruidDataSourceAutoConfigure.class)
@EnableFeignClients
@MapperScan({"com.qq.common.system.mapper"})
public class AccountServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AccountServerApplication.class, args);
    }
}
