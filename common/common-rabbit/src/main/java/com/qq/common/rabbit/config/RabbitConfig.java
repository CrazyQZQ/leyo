package com.qq.common.rabbit.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/6/16
 **/
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate getRabbitTemplate(){
        return new RabbitTemplate();
    }
}
