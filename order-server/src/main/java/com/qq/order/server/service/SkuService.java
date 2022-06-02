package com.qq.order.server.service;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.order.server.service.impl.ProductServiceCallBackImpl;
import com.qq.order.server.service.impl.SkuServiceCallBackImpl;
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
@FeignClient(name = "product-server", fallback = SkuServiceCallBackImpl.class)
public interface SkuService {

    @PostMapping("sku/getById")
    AjaxResult getSkuById(@RequestParam("id") Long id);

    @PostMapping("sku/reduceStock")
    AjaxResult reduceStock(@RequestParam("id") Long id, @RequestParam("stock") Integer stock);
}
