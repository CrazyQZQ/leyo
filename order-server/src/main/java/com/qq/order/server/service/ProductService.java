package com.qq.order.server.service;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.order.server.service.impl.ProductServiceCallBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description: feign调用商品服务
 * @Author QinQiang
 * @Date 2022/5/12
 **/
@Service
@FeignClient(name = "product-server", fallback = ProductServiceCallBackImpl.class)
public interface ProductService {

    /**
     * 查询商品列表
     * @param query
     * @return
     */
    @GetMapping("list")
    AjaxResult getProductList(BaseQuery query);
}
