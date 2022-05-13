package com.qq.product.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.system.pojo.SysProduct;

import java.io.IOException;
import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_product(商品表)】的数据库操作Service
* @createDate 2022-05-06 16:44:17
*/
public interface SysProductService extends IService<SysProduct> {

    /**
     * @description 新增商品
     * @param sysProduct 商品信息
     * @return
     */
    void addProduct(SysProduct sysProduct) throws IOException;

    /**
     * @description 根据商品id查询商品
     * @param id
     * @return
     */
    SysProduct getProductById(Long id);

    /**
     * @description 分页查询商品
     * @return
     */
    List<SysProduct> getProductList(BaseQuery query);

    /**
     * @description 更新商品
     * @param product
     * @return
     */
    int updateProduct(SysProduct product) throws IOException;

    /**
     * @description 删除商品
     * @param id
     * @return
     */
    int deleteProduct(Long id) throws IOException;

    /**
     * @description 减库存
     * @param id
     * @param stock
     * @return
     */
    void reduceStock(Long id, Integer stock) throws IOException;
}
