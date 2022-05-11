package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.es.service.EsService;
import com.qq.common.log.annotation.Log;
import com.qq.product.server.pojo.SysProduct;
import com.qq.product.server.service.SysProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product")
@Slf4j
@AllArgsConstructor
public class SysProductController extends BaseController {
    private final SysProductService sysProductService;
    private final EsService esService;

    @GetMapping("list")
    @Log(title = "product", funcDesc = "查询产品列表")
    public TableDataInfo getProductList() {
        startPage();
        List<SysProduct> productList = sysProductService.getProductList();
        TableDataInfo dataTable = getDataTable(productList);
        clearPage();
        return dataTable;
    }

    @GetMapping("info")
    @Log(title = "product", funcDesc = "查询产品详情")
    public AjaxResult getProductById(Long id) {
        return AjaxResult.success(sysProductService.getProductById(id));
    }

    @PostMapping("add")
    @Log(title = "product", funcDesc = "新增产品")
    public AjaxResult add(SysProduct product) {
        try {
            sysProductService.addProduct(product);
        } catch (IOException e) {
            log.error("新增产品失败", e);
            return AjaxResult.error("新增产品失败!");
        }
        return AjaxResult.success();
    }

    @PutMapping("update")
    @Log(title = "product", funcDesc = "修改产品")
    public AjaxResult update(SysProduct product) {
        try {
            sysProductService.updateProduct(product);
        } catch (IOException e) {
            log.error("修改产品失败", e);
            return AjaxResult.error("修改产品失败!");
        }
        return AjaxResult.success();
    }

    @PutMapping("delete")
    @Log(title = "product", funcDesc = "删除产品")
    public AjaxResult delete(Long id) {
        try {
            sysProductService.deleteProduct(id);
        } catch (IOException e) {
            log.error("删除产品失败", e);
            return AjaxResult.error("删除产品失败!");
        }
        return AjaxResult.success();
    }
}
