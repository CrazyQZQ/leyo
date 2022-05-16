package com.qq.product.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysBrand;
import com.qq.common.system.pojo.SysProductType;
import com.qq.product.server.service.SysBrandService;
import com.qq.product.server.service.SysProductTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/type")
@Slf4j
@AllArgsConstructor
public class SysProductTypeController extends BaseController {
    private final SysProductTypeService sysProductTypeService;
    private final SysBrandService sysBrandService;

    @GetMapping("list")
    @Log(title = "product_type", funcDesc = "查询产品品类列表")
    public AjaxResult getProductTypeList() {
        return AjaxResult.success(sysProductTypeService.queryTreeList());
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
            sysProductTypeService.save(productType);
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
            sysProductTypeService.updateById(productType);
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
            int count = sysProductTypeService.count(new QueryWrapper<SysProductType>().eq("parent_id", id));
            if (count > 0) {
                return AjaxResult.error("该品类下有子品类，不能删除!");
            }
            int brandCount = sysBrandService.count(new QueryWrapper<SysBrand>().eq("type_id", id));
            if (brandCount > 0) {
                return AjaxResult.error("该品类下有品牌，不能删除!");
            }
            sysProductTypeService.removeById(id);
        } catch (Exception e) {
            log.error("删除产品品类失败", e);
            return AjaxResult.error("删除产品品类失败!");
        }
        return AjaxResult.success();
    }
}
