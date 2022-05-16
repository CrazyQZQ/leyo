package com.qq.product.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.system.pojo.SysBrand;
import com.qq.common.system.pojo.SysProductType;
import com.qq.product.server.mapper.SysProductTypeMapper;
import com.qq.product.server.service.SysProductTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/16
 **/
@Service
public class SysProductTypeServiceImpl extends ServiceImpl<SysProductTypeMapper, SysProductType>
        implements SysProductTypeService {
    @Override
    public List<Tree<Long>> queryTreeList() {
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();
        List<SysProductType> productTypes = this.baseMapper.selectList(null);
        for (SysProductType type : productTypes) {
            TreeNode<Long> node = new TreeNode<>(type.getId(), type.getParentId(),type.getName(),type.getOrderNum());
            nodeList.add(node);
        }
        // 0表示最顶层的id是0
        return TreeUtil.build(nodeList, 0L);
    }
}
