package com.qq.product.server.receiver;

import com.alibaba.fastjson.JSON;
import com.qq.common.system.pojo.SysSkuEvaluation;
import com.qq.product.server.service.SysSkuEvaluationService;
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
 * @Description: 商品评价消费者
 * @Author QinQiang
 * @Date 2022/8/4
 **/
@Service
@Slf4j
@RabbitListener(queues = "skuEvaluation")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SkuEvaluationReceiver {

    private final SysSkuEvaluationService skuEvaluationService;

    /**
     * 消费String类型
     *
     * @param message
     * @param channel
     * @param msg
     */
    @RabbitHandler
    public void handlerMsg(Message message, Channel channel, String msg) {
        log.info("skuEvaluation消费者接受消息,内容：{}", msg);
        try {
            // 手动ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            System.out.println("手动ack失败");
        }
    }

    /**
     * 消费List<SysSkuEvaluation>类型
     *
     * @param message
     * @param channel
     * @param list
     */
    @RabbitHandler
    public void handlerMsg(Message message, Channel channel, List<SysSkuEvaluation> list) {
        log.info("skuEvaluation消费者接受消息,list：{}", JSON.toJSONString(list));
        try {
            skuEvaluationService.saveBatch(list);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.info("新增用户待评价商品数据失败：", e);
        }
    }
}
