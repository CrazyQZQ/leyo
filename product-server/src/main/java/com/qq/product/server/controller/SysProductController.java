package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.es.service.EsService;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysProduct;
import com.qq.product.server.service.SysProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductController extends BaseController<SysProduct> {
    private final SysProductService sysProductService;
    private final EsService esService;

    /**
     * 查询产品列表
     *
     * @param query
     * @return
     */
    @GetMapping("list")
    @Log(title = "product", funcDesc = "查询产品列表")
    public AjaxResult getProductList(BaseQuery query) {
        startPage();
        List<SysProduct> productList = sysProductService.getProductList(query);
        TableDataInfo dataTable = getDataTable(productList, null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * 查询产品详情
     *
     * @param id
     * @return
     */
    @GetMapping("info")
    @Log(title = "product", funcDesc = "查询产品详情")
    public AjaxResult getProductById(Long id) {
        return AjaxResult.success(sysProductService.getProductById(id));
    }

    /**
     * 新增产品
     *
     * @param product
     * @return
     */
    @PutMapping("add")
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

    /**
     * 修改产品
     *
     * @param product
     * @return
     */
    @PostMapping("update")
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

    /**
     * 删除产品
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
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
