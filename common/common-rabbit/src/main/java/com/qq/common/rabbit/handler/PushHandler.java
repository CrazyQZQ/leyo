package com.qq.common.rabbit.handler;

import com.alibaba.fastjson.JSON;
import com.qq.common.rabbit.pojo.PushData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @Description: 数据推送到mq
 * @Author QinQiang
 * @Date 2022/7/2
 **/
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PushHandler {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 推送数据
     *
     * @param data
     */
    public void pushData(PushData data) {
        log.info("PushHandler推送 ->topic:{}, routingKey:{}, data:{}", data.getTopicName(), data.getRoutingKey(), JSON.toJSONString(data.getData()));
        if (ObjectUtils.isEmpty(data) || StringUtils.isEmpty(data.getTopicName())
                || StringUtils.isEmpty(data.getRoutingKey()) || StringUtils.isEmpty(data.getData())) {
            log.debug("推送数据有误，取消推送");
            return;
        }
        // 如果存在事务，在事务之后推送，不存在则直接推送
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    send(data);
                }
            });
        } else {
            send(data);
        }
    }

    /**
     * 发送消息
     * @param data
     */
    private void send(PushData data) {
        rabbitTemplate.convertAndSend(data.getTopicName(), data.getRoutingKey(), data.getData(), message -> {
            if(!StringUtils.isEmpty(data.getDelayTime())) {
                message.getMessageProperties().setExpiration(data.getDelayTime());
            }
            return message;
        });
    }
}
