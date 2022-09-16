package com.qq.product.server.service;

import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.system.pojo.SysProduct;
import com.qq.common.system.pojo.SysProductCollection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_product_collection(商品收藏表)】的数据库操作Service
* @createDate 2022-08-04 14:37:18
*/
public interface SysProductCollectionService extends IService<SysProductCollection> {

    /**
     * 查询收藏商品
     *
     * @param query
     * @return
     */
    List<SysProduct> listProductCollections(BaseQuery query);

    /**
     * 收藏商品
     * @param productCollection
     */
    void collect(SysProductCollection productCollection);
}
