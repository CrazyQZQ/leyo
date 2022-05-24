package com.qq.product.server.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.system.pojo.SysBrand;

import java.util.List;

public interface SysBrandService extends IService<SysBrand> {
    List<Tree<Long>> queryTreeList();

    void addBrand(SysBrand sysBrand);

    void updateBrand(SysBrand sysBrand);

    void deleteBrand(Long id);

    List<SysBrand> list(BaseQuery query);
}
