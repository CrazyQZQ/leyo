package com.qq.order.server.service.impl;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.order.server.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/12
 **/
@Service
public class ProductServiceCallBackImpl implements ProductService {

    @Override
    public AjaxResult getProductList(BaseQuery query) {
        return AjaxResult.error("商品服务不可用");
    }
}
