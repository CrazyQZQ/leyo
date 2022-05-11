package com.qq.product.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.product.server.pojo.SysProduct;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_product(商品表)】的数据库操作Mapper
 * @createDate 2022-05-06 16:44:17
 * @Entity com.qq.common.system.pojo.SysProduct
 */
public interface SysProductMapper extends BaseMapper<SysProduct> {
    SysProduct getProductById(Long id);

    List<SysProduct> getProductList();
}




