package com.qq.product.server.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.rabbit.config.HotSaleTopicConfig;
import com.qq.common.rabbit.handler.PushHandler;
import com.qq.common.rabbit.pojo.PushData;
import com.qq.common.system.pojo.SysOrderDetail;
import com.qq.common.system.pojo.SysProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        PushData<List<SysOrderDetail>> data = new PushData<>();
        List<SysOrderDetail> orderDetailList = new ArrayList<>();
        SysOrderDetail detail = new SysOrderDetail();
        detail.setSkuId(1111l);
        detail.setCount(12);
        orderDetailList.add(detail);
        // SysProduct product = new SysProduct();
        // product.setName("444");
        data.setData(orderDetailList);
        data.setTopicName(HotSaleTopicConfig.TOPIC_NAME);
        data.setRoutingKey(HotSaleTopicConfig.ROUTING_KEY);
        log.info("开始推送消息");
        pushHandler.pushData(data);
        return AjaxResult.success();
    }
}
