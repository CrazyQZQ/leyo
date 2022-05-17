package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysBrand;
import com.qq.product.server.service.SysBrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/brand")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductBrandController extends BaseController {
    private final SysBrandService sysBrandService;

    @GetMapping("list")
    @Log(title = "product_brand", funcDesc = "查询品牌树")
    public AjaxResult getProductBrandTree() {
        return AjaxResult.success(sysBrandService.queryTreeList());
    }

    @GetMapping("info")
    @Log(title = "product_brand", funcDesc = "查询品牌详情")
    public AjaxResult getBrandById(Long id) {
        return AjaxResult.success(sysBrandService.getById(id));
    }

    @PutMapping("add")
    @Log(title = "product_brand", funcDesc = "新增品牌")
    public AjaxResult add(SysBrand brand) {
        try {
            sysBrandService.addBrand(brand);
        } catch (Exception e) {
            log.error("新增品牌失败", e);
            return AjaxResult.error("新增品牌失败!");
        }
        return AjaxResult.success();
    }

    @PostMapping("update")
    @Log(title = "product_brand", funcDesc = "修改品牌")
    public AjaxResult update(SysBrand brand) {
        try {
            sysBrandService.updateBrand(brand);
        } catch (Exception e) {
            log.error("修改品牌失败", e);
            return AjaxResult.error("修改产品失败!");
        }
        return AjaxResult.success();
    }

    @DeleteMapping("delete")
    @Log(title = "product_brand", funcDesc = "删除品牌")
    public AjaxResult delete(Long id) {
        try {
            sysBrandService.deleteBrand(id);
        } catch (Exception e) {
            log.error("删除品牌失败", e);
            return AjaxResult.error("删除品牌失败!");
        }
        return AjaxResult.success();
    }
}
