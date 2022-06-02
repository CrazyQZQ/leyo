package com.qq.order.server.service.impl;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.SysSku;
import com.qq.order.server.service.SkuService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/6/2
 **/
@Service
public class SkuServiceCallBackImpl implements SkuService {
    @Override
    public AjaxResult getSkuById(Long id) {
        return AjaxResult.error("商品服务不可用");
    }

    @Override
    public AjaxResult reduceStock(@RequestParam Long id, @RequestParam Integer stock) {
        return AjaxResult.error("商品服务不可用");
    }
}

