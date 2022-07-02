package com.qq.product.server.receiver;

import com.alibaba.fastjson.JSON;
import com.qq.common.system.pojo.SysOrderDetail;
import com.qq.common.system.pojo.SysProduct;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/6/16
 **/
@Service
@Slf4j
@RabbitListener(queues = "hotSale")
public class HotSaleReceiver {

    @RabbitHandler
    public void handlerMsg(Message message, Channel channel, String msg) {
        log.info("hotSale消费者接受消息,内容：{}", msg);
        try {
            // 手动ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            System.out.println("手动ack失败");
        }
    }

    @RabbitHandler
    public void handlerMsg(Message message, Channel channel, List<SysOrderDetail> orderDetailList) {
        log.info("hotSale消费者接受消息,内容：{}", JSON.toJSONString(orderDetailList));
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            System.out.println("手动ack失败");
        }
    }
}
