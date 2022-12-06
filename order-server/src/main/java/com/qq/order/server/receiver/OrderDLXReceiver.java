package com.qq.order.server.receiver;

import com.qq.common.rabbit.config.OrderDelayQueueRabbitConfig;
import com.qq.common.system.pojo.SysOrder;
import com.qq.order.server.mapper.SysOrderMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 热卖商品消费者
 * @Author QinQiang
 * @Date 2022/6/16
 **/
@Service
@Slf4j
@RabbitListener(queues = OrderDelayQueueRabbitConfig.DLX_QUEUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderDLXReceiver {

    private final SysOrderMapper orderMapper;

    /**
     * 关闭订单
     *
     * @param message
     * @param channel
     * @param msg
     */
    @RabbitHandler
    public void handlerMsg(Message message, Channel channel, Long msg) {
        log.info("订单死信队列消费者接受消息,订单id：{}", msg);
        SysOrder order = new SysOrder();
        order.setId(msg);
        order.setOrderStatus(5);
        orderMapper.updateById(order);
    }
}
