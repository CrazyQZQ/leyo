package com.qq.order.server;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description: OrderServerApplication
 * @Author QinQiang
 * @Date 2022/4/28
 **/
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan({"com.qq.**.mapper"})
@EnableScheduling
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }

}
