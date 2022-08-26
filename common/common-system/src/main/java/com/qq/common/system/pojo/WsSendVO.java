package com.qq.common.system.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/8/25
 **/
@Data
public class WsSendVO {

    private List<Long> userIds;

    private String message;
}
