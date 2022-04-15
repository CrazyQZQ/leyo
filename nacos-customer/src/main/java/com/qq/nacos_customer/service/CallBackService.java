package com.qq.nacos_customer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/2
 **/
@Service
public class CallBackService implements TestService {
    @Override
    public String echo(@PathVariable("str") String str) {
        return "callBack" + str;
    }
}
