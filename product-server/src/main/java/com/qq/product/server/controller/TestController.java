package com.qq.product.server.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.rabbit.config.ProductTopicConfig;
import com.qq.common.rabbit.handler.PushHandler;
import com.qq.common.rabbit.pojo.PushData;
import com.qq.product.server.service.SysSkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/7/2
 **/
@RestController
@RequestMapping("product")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private final PushHandler pushHandler;

    @GetMapping("test")
    public AjaxResult getSkuList() {
        PushData<List<Long>> data = new PushData<>();
        data.setData(Arrays.asList(1l));
        data.setTopicName(ProductTopicConfig.TOPIC_NAME);
        data.setRoutingKey(ProductTopicConfig.HOT_SALE_ROUTING_KEY);
        log.info("开始推送消息");
        pushHandler.pushData(data);
        return AjaxResult.success();
    }
}
