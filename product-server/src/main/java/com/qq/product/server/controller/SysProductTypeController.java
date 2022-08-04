package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysProductType;
import com.qq.product.server.service.SysProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 商品品类管理
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/type")
@Slf4j
@Api(tags = "商品品类管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductTypeController extends BaseController {
    private final SysProductTypeService sysProductTypeService;

    /**
     * 查询商品品类树
     *
     * @return
     */
    @ApiOperation("查询商品品类树")
    @GetMapping("tree")
    @Log(title = "product_type", funcDesc = "查询商品品类树")
    public AjaxResult getProductTypeTreeList() {
        return AjaxResult.success(sysProductTypeService.queryTreeList());
    }

    /**
     * 查询商品品类列表
     *
     * @param query
     * @return
     */
    @ApiOperation("查询商品品类列表")
    @GetMapping("list")
    @Log(title = "product_type", funcDesc = "查询商品品类列表")
    public AjaxResult getProductTypeList(BaseQuery query) {
        startPage();
        List<SysProductType> types = sysProductTypeService.list(query);
        TableDataInfo dataTable = getDataTable(types, null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * 查询商品品类详情
     *
     * @param id
     * @return
     */
    @ApiOperation("查询商品品类详情")
    @GetMapping("info")
    @Log(title = "product_type", funcDesc = "查询商品品类详情")
    public AjaxResult getProductTypeById(@ApiParam("商品品类id") Long id) {
        return AjaxResult.success(sysProductTypeService.getById(id));
    }

    /**
     * 新增商品品类
     *
     * @param productType
     * @return
     */
    @ApiOperation("新增商品品类")
    @PostMapping("add")
    @Log(title = "product_type", funcDesc = "新增商品品类")
    public AjaxResult add(@RequestBody SysProductType productType) {
        try {
            sysProductTypeService.addProductType(productType);
        } catch (Exception e) {
            log.error("新增商品品类失败", e);
            return AjaxResult.error("新增商品品类失败!");
        }
        return AjaxResult.success();
    }

    /**
     * 修改商品品类
     *
     * @param productType
     * @return
     */
    @ApiOperation("修改商品品类")
    @PutMapping("update")
    @Log(title = "product_type", funcDesc = "修改商品品类")
    public AjaxResult update(SysProductType productType) {
        try {
            sysProductTypeService.updateProductType(productType);
        } catch (Exception e) {
            log.error("修改商品品类失败", e);
            return AjaxResult.error("修改商品品类失败!");
        }
        return AjaxResult.success();
    }

    /**
     * 删除商品品类
     *
     * @param id
     * @return
     */
    @ApiOperation("删除商品品类")
    @DeleteMapping("delete")
    @Log(title = "product_type", funcDesc = "删除商品品类")
    public AjaxResult delete(@ApiParam("商品品类id") Long id) {
        try {
            sysProductTypeService.deleteProductType(id);
        } catch (Exception e) {
            log.error("删除商品品类失败", e);
            return AjaxResult.error("删除商品品类失败!");
        }
        return AjaxResult.success();
    }
}
