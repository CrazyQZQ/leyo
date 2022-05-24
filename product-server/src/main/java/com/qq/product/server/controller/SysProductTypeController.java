package com.qq.product.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysProductType;
import com.qq.product.server.service.SysBrandService;
import com.qq.product.server.service.SysProductTypeService;
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
@RequestMapping("product/type")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductTypeController extends BaseController {
    private final SysProductTypeService sysProductTypeService;
    private final SysBrandService sysBrandService;

    @GetMapping("tree")
    @Log(title = "product_type", funcDesc = "查询产品品类数")
    public AjaxResult getProductTypeTreeList() {
        return AjaxResult.success(sysProductTypeService.queryTreeList());
    }

    @GetMapping("list")
    @Log(title = "product_type", funcDesc = "查询产品品类列表")
    public AjaxResult getProductTypeList(@RequestParam("parentId") Long parentId) {
        return AjaxResult.success(sysProductTypeService.list(new QueryWrapper<SysProductType>().eq("parent_id", parentId)));
    }

    @GetMapping("info")
    @Log(title = "product_type", funcDesc = "查询产品品类详情")
    public AjaxResult getProductTypeById(Long id) {
        return AjaxResult.success(sysProductTypeService.getById(id));
    }

    @PutMapping("add")
    @Log(title = "product_type", funcDesc = "新增产品品类")
    public AjaxResult add(SysProductType productType) {
        try {
            sysProductTypeService.addProductType(productType);
        } catch (Exception e) {
            log.error("新增产品品类失败", e);
            return AjaxResult.error("新增产品品类失败!");
        }
        return AjaxResult.success();
    }

    @PostMapping("update")
    @Log(title = "product_type", funcDesc = "修改产品品类")
    public AjaxResult update(SysProductType productType) {
        try {
            sysProductTypeService.updateProductType(productType);
        } catch (Exception e) {
            log.error("修改产品品类失败", e);
            return AjaxResult.error("修改产品品类失败!");
        }
        return AjaxResult.success();
    }

    @DeleteMapping("delete")
    @Log(title = "product_type", funcDesc = "删除产品品类")
    public AjaxResult delete(Long id) {
        try {
            sysProductTypeService.deleteProductType(id);
        } catch (Exception e) {
            log.error("删除产品品类失败", e);
            return AjaxResult.error("删除产品品类失败!");
        }
        return AjaxResult.success();
    }
}
