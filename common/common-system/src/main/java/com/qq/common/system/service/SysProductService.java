package com.qq.common.system.service;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.SysProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Administrator
* @description 针对表【sys_product(商品表)】的数据库操作Service
* @createDate 2022-05-06 16:44:17
*/
public interface SysProductService extends IService<SysProduct> {

    /**
     * @description 新增商品
     * @param images 商品图片
     * @param sysProduct 商品信息
     * @return
     */
    AjaxResult addProduct(MultipartFile[] images, SysProduct sysProduct);
}
