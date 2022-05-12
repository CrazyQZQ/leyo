package com.qq.order.server.service.impl;

import com.qq.common.core.constant.HttpStatus;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.order.server.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/12
 **/
public class ProductServiceCallBackImpl implements ProductService {
    @Override
    public AjaxResult reduceStock(@RequestParam Long id, @RequestParam Integer stock) {
        return AjaxResult.error("商品服务不可用");
    }

    @Override
    public TableDataInfo getProductList(BaseQuery query) {
        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setCode(HttpStatus.ERROR);
        tableDataInfo.setMsg("商品服务不可用");
        return tableDataInfo;
    }
}
