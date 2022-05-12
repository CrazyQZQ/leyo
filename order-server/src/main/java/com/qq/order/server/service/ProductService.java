package com.qq.order.server.service;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.order.server.service.impl.ProductServiceCallBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/12
 **/
@Service
@FeignClient(name = "product-server", fallback = ProductServiceCallBackImpl.class)
public interface ProductService {

    @PostMapping("reduceStock")
    AjaxResult reduceStock(@RequestParam Long id, @RequestParam Integer stock);

    @GetMapping("list")
    TableDataInfo getProductList(BaseQuery query);
}
