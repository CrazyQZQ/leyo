package com.qq.product.server.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysProductType;

import java.util.List;

public interface SysProductTypeService extends IService<SysProductType> {
    List<Tree<Long>> queryTreeList();

    void addProductType(SysProductType productType);

    void updateProductType(SysProductType productType);

    void deleteProductType(Long id);
}
