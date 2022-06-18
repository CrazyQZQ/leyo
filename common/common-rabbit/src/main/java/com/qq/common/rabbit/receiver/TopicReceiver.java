package com.qq.common.rabbit.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/6/16
 **/
@Component
public class TopicReceiver {
    @RabbitListener(queues = "xiaomi")
    public void handlerXiaomi(String msg) {
        System.out.println("handlerXiaomi >>" + msg);
    }

    @RabbitListener(queues = "xiaomi")
    public void handlerHuawei(String msg) {
        System.out.println("handlerXiaomi2 >>" + msg);
    }

    @RabbitListener(queues = "phone")
    public void handlerPhone(String msg) {
        System.out.println("handlerPhone >>" + msg);
    }
}
