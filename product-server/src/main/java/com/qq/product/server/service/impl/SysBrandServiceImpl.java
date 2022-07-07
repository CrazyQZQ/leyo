package com.qq.product.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.system.mapper.SysObjectImagesMapper;
import com.qq.common.system.pojo.SysBrand;
import com.qq.common.system.pojo.SysObjectImages;
import com.qq.common.system.service.MinIoService;
import com.qq.common.system.utils.OauthUtils;
import com.qq.product.server.mapper.SysBrandMapper;
import com.qq.product.server.service.SysBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/16
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysBrandServiceImpl extends ServiceImpl<SysBrandMapper, SysBrand>
        implements SysBrandService {

    private final MinIoService minIoService;
    private final SysObjectImagesMapper sysObjectImagesMapper;

    /**
     * 查询品牌树
     *
     * @return
     */
    @Override
    public List<Tree<Long>> queryTreeList() {
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();
        List<SysBrand> brands = this.baseMapper.getBrandList(null);
        for (SysBrand brand : brands) {
            TreeNode<Long> node = new TreeNode<>(brand.getId(), brand.getParentId(), brand.getName(), brand.getOrderNum());
            Map<String, Object> extra = MapUtil.newHashMap();
            extra.put("imageUrls", brand.getImageUrls());
            node.setExtra(extra);
            nodeList.add(node);
        }
        // 0表示最顶层的id是0
        return TreeUtil.build(nodeList, 0L);
    }

    /**
     * 新增品牌
     *
     * @param sysBrand
     */
    @Override
    @Transactional
    public void addBrand(SysBrand sysBrand) {
        sysBrand.setCreateBy(OauthUtils.getCurrentUserName());
        sysBrand.setCreateTime(new Date());
        this.baseMapper.insert(sysBrand);
        MultipartFile image = sysBrand.getImage();
        if (image != null) {
            String upload = minIoService.upload(image);
            SysObjectImages sysObjectImages = new SysObjectImages();
            sysObjectImages.setObjectId(sysBrand.getId());
            sysObjectImages.setImageUrl(upload);
            sysObjectImages.setObjectType(2);
            sysObjectImagesMapper.insert(sysObjectImages);

        }
    }

    /**
     * 修改品牌
     *
     * @param sysBrand
     */
    @Override
    @Transactional
    public void updateBrand(SysBrand sysBrand) {
        if (sysBrand.getId() == null) {
            throw new ServiceException("品牌id不能为空！");
        }
        sysBrand.setUpdateBy(OauthUtils.getCurrentUserName());
        sysBrand.setUpdateTime(new Date());
        int i = this.baseMapper.updateById(sysBrand);
        if (i == 0) {
            throw new ServiceException("品牌不存在！");
        }
        MultipartFile image = sysBrand.getImage();
        if (image != null) {
            minIoService.deleteFileByFullPath(sysBrand.getImageUrls());
            String upload = minIoService.upload(image);
            SysObjectImages sysObjectImages = new SysObjectImages();
            sysObjectImages.setImageUrl(upload);
            sysObjectImagesMapper.update(sysObjectImages, new UpdateWrapper<SysObjectImages>().eq("object_id", sysBrand.getId()));
        }
    }

    /**
     * 删除品牌
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteBrand(Long id) {
        if (id == null) {
            throw new ServiceException("品牌id不能为空！");
        }
        SysBrand sysBrand = this.baseMapper.selectById(id);
        if (sysBrand == null) {
            throw new ServiceException("品牌不存在！");
        }
        Integer children = this.baseMapper.selectCount(new QueryWrapper<SysBrand>().eq("parent_id", id));
        if (children > 0) {
            throw new ServiceException("该品牌下有子品牌，不能删除！");
        }
        List<String> images = sysObjectImagesMapper.selectList(new QueryWrapper<SysObjectImages>().eq("object_id", id))
                .stream().map(SysObjectImages::getImageUrl)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(images)) {
            sysObjectImagesMapper.delete(new QueryWrapper<SysObjectImages>().eq("object_id", id));
            minIoService.deleteFileByFullPath(images);
        }
    }

    /**
     * 查询品牌列表
     *
     * @param query
     * @return
     */
    @Override
    public List<SysBrand> list(BaseQuery query) {
        return this.baseMapper.getBrandList(query);
    }
}
