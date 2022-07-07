package com.qq.product.server.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.system.pojo.SysBrand;

import java.util.List;

public interface SysBrandService extends IService<SysBrand> {

    /**
     * 查询品牌树
     *
     * @return
     */
    List<Tree<Long>> queryTreeList();

    /**
     * 新增品牌
     *
     * @param sysBrand
     */
    void addBrand(SysBrand sysBrand);

    /**
     * 修改品牌
     *
     * @param sysBrand
     */
    void updateBrand(SysBrand sysBrand);

    /**
     * 删除品牌
     *
     * @param id
     */
    void deleteBrand(Long id);

    /**
     * 查询品牌列表
     *
     * @param query
     * @return
     */
    List<SysBrand> list(BaseQuery query);
}
