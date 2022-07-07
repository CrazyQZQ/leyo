package com.qq.common.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/6/16
 **/
@Configuration
public class HotSaleTopicConfig {
    public static final String TOPIC_NAME = "hot-sale-topic";

    public static final String ROUTING_KEY = "hotSale";

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_NAME, true, false);
    }

    @Bean
    Queue hotSale() {
        return new Queue("hotSale");
    }

    /**
     * routingKey以hotSale开头转发到此队列
     *
     * @return
     */
    @Bean
    Binding hotSaleBinding() {
        return BindingBuilder.bind(hotSale()).to(topicExchange()).with("hotSale.#");
    }
}
