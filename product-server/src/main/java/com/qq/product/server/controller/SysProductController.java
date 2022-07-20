package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.es.service.EsService;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysProduct;
import com.qq.product.server.service.SysProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 商品管理
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product")
@Slf4j
@Api(tags = "商品管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductController extends BaseController<SysProduct> {
    private final SysProductService sysProductService;
    private final EsService esService;

    /**
     * 查询商品列表
     *
     * @param query
     * @return
     */
    @ApiOperation("查询商品列表")
    @GetMapping("list")
    @Log(title = "product", funcDesc = "查询商品列表")
    public AjaxResult getProductList(BaseQuery query) {
        startPage();
        List<SysProduct> productList = sysProductService.getProductList(query);
        TableDataInfo dataTable = getDataTable(productList, null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    @ApiOperation("查询商品详情")
    @GetMapping("info")
    @Log(title = "product", funcDesc = "查询商品详情")
    public AjaxResult getProductById(@ApiParam("商品id") Long id) {
        return AjaxResult.success(sysProductService.getProductById(id));
    }

    /**
     * 新增商品
     *
     * @param product
     * @return
     */
    @ApiOperation("新增商品")
    @PutMapping("add")
    @Log(title = "product", funcDesc = "新增商品")
    public AjaxResult add(@RequestBody SysProduct product) {
        try {
            sysProductService.addProduct(product);
        } catch (IOException e) {
            log.error("新增商品失败", e);
            return AjaxResult.error("新增商品失败!");
        }
        return AjaxResult.success();
    }

    /**
     * 修改商品
     *
     * @param product
     * @return
     */
    @ApiOperation("修改商品")
    @PostMapping("update")
    @Log(title = "product", funcDesc = "修改商品")
    public AjaxResult update(@RequestBody SysProduct product) {
        try {
            sysProductService.updateProduct(product);
        } catch (IOException e) {
            log.error("修改商品失败", e);
            return AjaxResult.error("修改商品失败!");
        }
        return AjaxResult.success();
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    @ApiOperation("删除商品")
    @DeleteMapping("delete")
    @Log(title = "product", funcDesc = "删除商品")
    public AjaxResult delete(@ApiParam("商品id") Long id) {
        try {
            sysProductService.deleteProduct(id);
        } catch (IOException e) {
            log.error("删除商品失败", e);
            return AjaxResult.error("删除商品失败!");
        }
        return AjaxResult.success();
    }
}
