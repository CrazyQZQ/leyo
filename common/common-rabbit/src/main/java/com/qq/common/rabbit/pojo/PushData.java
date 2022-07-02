package com.qq.common.rabbit.pojo;

import lombok.Data;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/7/2
 **/
@Data
public class PushData<T> {
    /**
     * topicName
     */
    private String topicName;
    /**
     * routingKey
     */
    private String routingKey;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 数据体
     */
    T data;
}
