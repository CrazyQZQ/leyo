package com.qq.product.server.receiver;

import com.alibaba.fastjson.JSON;
import com.qq.common.rabbit.config.ProductTopicConfig;
import com.qq.common.system.pojo.SysOrderDetail;
import com.qq.common.system.pojo.SysProduct;
import com.qq.product.server.service.SysSkuService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 热卖商品消费者
 * @Author QinQiang
 * @Date 2022/6/16
 **/
@Service
@Slf4j
@RabbitListener(queues = ProductTopicConfig.HOT_SALE_ROUTING_KEY)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HotSaleReceiver {

    private final SysSkuService skuService;

    /**
     * 消费String类型
     *
     * @param message
     * @param channel
     * @param msg
     */
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

    /**
     * 消费List<Long>类型
     *
     * @param message
     * @param channel
     * @param skuIds
     */
    @RabbitHandler
    public void handlerMsg(Message message, Channel channel, List<Long> skuIds) {
        log.info("hotSale消费者接受消息,skuIds：{}", JSON.toJSONString(skuIds));
        try {
            skuService.updateSkuInEs(skuIds);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.info("修改es热卖数据失败：", e);
        }
    }
}
