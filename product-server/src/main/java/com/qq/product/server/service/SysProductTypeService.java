package com.qq.product.server.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.system.pojo.SysProductType;

import java.util.List;

public interface SysProductTypeService extends IService<SysProductType> {
    /**
     * 查询品类树
     *
     * @return
     */
    List<Tree<Long>> queryTreeList();

    /**
     * 新增品类
     *
     * @param productType
     */
    void addProductType(SysProductType productType);

    /**
     * 修改品类
     *
     * @param productType
     */
    void updateProductType(SysProductType productType);

    /**
     * 删除品类
     *
     * @param id
     */
    void deleteProductType(Long id);

    /**
     * 查询品类列表
     *
     * @param query
     * @return
     */
    List<SysProductType> list(BaseQuery query);
}
