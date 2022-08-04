package com.qq.common.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 商品模块topic配置
 * @Author QinQiang
 * @Date 2022/6/16
 **/
@Configuration
public class ProductTopicConfig {
    public static final String TOPIC_NAME = "product-topic";

    public static final String HOT_SALE_ROUTING_KEY = "hotSale";

    public static final String SKU_EVALUATION_ROUTING_KEY = "skuEvaluation";

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_NAME, true, false);
    }

    @Bean
    Queue hotSale() {
        return new Queue(HOT_SALE_ROUTING_KEY);
    }

    @Bean
    Queue skuEvaluation() {
        return new Queue(SKU_EVALUATION_ROUTING_KEY);
    }

    /**
     * routingKey以hotSale开头转发到此队列
     *
     * @return
     */
    @Bean
    Binding hotSaleBinding() {
        return BindingBuilder.bind(hotSale()).to(topicExchange()).with(HOT_SALE_ROUTING_KEY + ".#");
    }

    /**
     * routingKey以skuEvaluation开头转发到此队列
     *
     * @return
     */
    @Bean
    Binding skuEvaluationBinding() {
        return BindingBuilder.bind(skuEvaluation()).to(topicExchange()).with(SKU_EVALUATION_ROUTING_KEY + ".#");
    }
}
