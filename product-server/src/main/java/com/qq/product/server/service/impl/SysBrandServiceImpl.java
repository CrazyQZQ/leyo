package com.qq.product.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.system.pojo.SysBrand;
import com.qq.product.server.mapper.SysBrandMapper;
import com.qq.product.server.service.SysBrandService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/16
 **/
@Service
public class SysBrandServiceImpl extends ServiceImpl<SysBrandMapper, SysBrand>
        implements SysBrandService {

    @Override
    public List<Tree<Long>> queryTreeList() {
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();
        List<SysBrand> brands = this.baseMapper.selectList(null);
        for (SysBrand brand : brands) {
            TreeNode<Long> node = new TreeNode<>(brand.getId(), brand.getParentId(),brand.getName(),brand.getOrderNum());
            nodeList.add(node);
        }
        // 0表示最顶层的id是0
        return TreeUtil.build(nodeList, 0L);
    }
}
