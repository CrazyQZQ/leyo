package com.qq.product.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysBrand;
import com.qq.common.system.pojo.SysProductType;
import com.qq.product.server.service.SysBrandService;
import com.qq.product.server.service.SysProductTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 查询产品品类树
     *
     * @return
     */
    @GetMapping("tree")
    @Log(title = "product_type", funcDesc = "查询产品品类树")
    public AjaxResult getProductTypeTreeList() {
        return AjaxResult.success(sysProductTypeService.queryTreeList());
    }

    /**
     * 查询产品品类列表
     *
     * @param query
     * @return
     */
    @GetMapping("list")
    @Log(title = "product_type", funcDesc = "查询产品品类列表")
    public TableDataInfo getProductTypeList(BaseQuery query) {
        startPage();
        List<SysProductType> types = sysProductTypeService.list(query);
        TableDataInfo dataTable = getDataTable(types);
        clearPage();
        return dataTable;
    }

    /**
     * 查询产品品类详情
     *
     * @param id
     * @return
     */
    @GetMapping("info")
    @Log(title = "product_type", funcDesc = "查询产品品类详情")
    public AjaxResult getProductTypeById(Long id) {
        return AjaxResult.success(sysProductTypeService.getById(id));
    }

    /**
     * 新增产品品类
     *
     * @param productType
     * @return
     */
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

    /**
     * 修改产品品类
     *
     * @param productType
     * @return
     */
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

    /**
     * 删除产品品类
     *
     * @param id
     * @return
     */
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
