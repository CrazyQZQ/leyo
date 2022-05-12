package com.qq.order.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.order.server.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("order")
@Slf4j
@AllArgsConstructor
public class OrderController extends BaseController {
    private final ProductService productService;

    @RequestMapping("/productInfo")
    public TableDataInfo productInfo(BaseQuery query) {
        log.info("订单服务开始查询商品信息");
        return productService.getProductList(query);
    }
}
