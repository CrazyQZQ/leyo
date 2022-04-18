package com.qq.nacos_customer;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.qq.nacos_customer", "com.qq.common.system"}, exclude = DruidDataSourceAutoConfigure.class)
@EnableFeignClients
@MapperScan({"com.qq.nacos_customer.mapper", "com.qq.common.system.mapper"})
public class NacosCustomerApplication {


    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(NacosCustomerApplication.class, args);
        String property = context.getEnvironment().getProperty("spring.datasource.druid.master.username");
        System.out.println("spring.datasource.druid.master.username:" + property);
    }
}
