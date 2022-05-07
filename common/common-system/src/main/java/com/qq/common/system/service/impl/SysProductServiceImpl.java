package com.qq.common.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.mapper.SysProductImagesMapper;
import com.qq.common.system.pojo.SysProduct;
import com.qq.common.system.pojo.SysProductImages;
import com.qq.common.system.service.MinIoService;
import com.qq.common.system.service.SysProductService;
import com.qq.common.system.mapper.SysProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Administrator
* @description 针对表【sys_product(商品表)】的数据库操作Service实现
* @createDate 2022-05-06 16:44:17
*/
@Service
@AllArgsConstructor
public class SysProductServiceImpl extends ServiceImpl<SysProductMapper, SysProduct>
    implements SysProductService{

    private final MinIoService minIoService;
    private final SysProductImagesMapper sysProductImagesMapper;

    @Override
    public AjaxResult addProduct(MultipartFile[] images, SysProduct sysProduct) {
        this.baseMapper.insert(sysProduct);
        for (MultipartFile image : images) {
            String upload = minIoService.upload(image);
            SysProductImages sysProductImages = new SysProductImages();
            sysProductImages.setProductId(sysProduct.getId());
            sysProductImages.setImageUrl(upload);
            sysProductImagesMapper.insert(sysProductImages);
        }
        return AjaxResult.success();
    }
}




