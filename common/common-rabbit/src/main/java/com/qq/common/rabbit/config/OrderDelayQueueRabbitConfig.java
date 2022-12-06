package com.qq.common.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 订单延时队列，订单签收后7天关闭订单
 * @Author QinQiang
 * @Date 2022/12/6
 **/
@Configuration
public class OrderDelayQueueRabbitConfig {

    /**
     * 订单自动关闭时间，7天 1000*3600*24*7 = 604800000
     */
    public static final String ORDER_CLOSE_EXPIRATION = "604800000";
    /**
     * 死信队列
     */
    public static final String DLX_QUEUE = "queue.dlx";
    /**
     * 死信交换机
     */
    public static final String DLX_EXCHANGE = "exchange.dlx";
    /**
     * 死信队列与死信交换机绑定的routing-key
     */
    public static final String DLX_ROUTING_KEY = "routingKey.dlx";
    /**
     * 订单的延时队列
     */
    public static final String ORDER_QUEUE = "queue.order";
    /**
     * 订单交换机
     */
    public static final String ORDER_EXCHANGE = "exchange.order";
    /**
     * 延时队列与订单交换机绑定的routing-key
     */
    public static final String ORDER_ROUTING_KEY = "routingkey.order";

    /**
     * 定义死信队列
     **/
    @Bean
    public Queue dlxQueue(){
        return new Queue(DLX_QUEUE,true);
    }

    /**
     * 定义死信交换机
     **/
    @Bean
    public DirectExchange dlxExchange(){
        return new DirectExchange(DLX_EXCHANGE, true, false);
    }


    /**
     * 死信队列和死信交换机绑定
     * 设置路由键：routingkey.dlx
     **/
    @Bean
    Binding bindingDLX(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY);
    }


    /**
     * 订单延时队列
     * 设置队列里的死信转发到的DLX名称
     * 设置死信在转发时携带的 routing-key 名称
     **/
    @Bean
    public Queue orderQueue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", DLX_EXCHANGE);
        params.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return new Queue(ORDER_QUEUE, true, false, false, params);
    }

    /**
     * 订单交换机
     **/
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    /**
     * 把订单队列和订单交换机绑定在一起
     **/
    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_ROUTING_KEY);
    }
}
