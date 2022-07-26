package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.es.service.EsService;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysSku;
import com.qq.product.server.constants.ProductConstants;
import com.qq.product.server.service.SysSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 商品sku管理
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/sku")
@Slf4j
@Api(tags = "商品sku管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSkuController extends BaseController {
    private final SysSkuService skuService;
    private final EsService esService;

    /**
     * 查询商品sku列表
     *
     * @param productId
     * @return
     */
    @ApiOperation("查询商品sku列表")
    @GetMapping("list")
    @Log(title = "product_sku", funcDesc = "查询商品sku列表")
    public AjaxResult getSkuList(@ApiParam("商品id") @RequestParam(required = false) Long productId) {
        startPage();
        List<SysSku> list = skuService.list(productId);
        TableDataInfo dataTable = getDataTable(list, null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * id查询商品sku
     *
     * @param id
     * @return
     */
    @ApiOperation("id查询商品sku")
    @GetMapping("getById")
    @Log(title = "product_sku", funcDesc = "id查询商品sku")
    public AjaxResult getSkuById(@ApiParam("skuId") @RequestParam Long id) {
        return AjaxResult.success(skuService.getSkuById(id));
    }

    /**
     * 新增商品sku
     *
     * @param sku
     * @return
     */
    @ApiOperation("新增商品sku")
    @PutMapping("add")
    @Log(title = "product_sku", funcDesc = "新增商品sku")
    public AjaxResult add(@RequestBody SysSku sku) {
        try {
            skuService.save(sku);
            esService.addDoc(ProductConstants.SKU_INDEX, sku.getId().toString(), skuService.getSkuById(sku.getId()));
        } catch (Exception e) {
            log.error("新增商品sku异常，", e);
            return AjaxResult.error("系统繁忙");
        }
        return AjaxResult.success();
    }

    /**
     * 修改商品sku
     *
     * @param sku
     * @return
     */
    @ApiOperation("修改商品sku")
    @PostMapping("update")
    @Log(title = "product_sku", funcDesc = "修改商品sku")
    public AjaxResult update(@RequestBody SysSku sku) {
        try {
            skuService.updateById(sku);
            esService.updateDoc(ProductConstants.SKU_INDEX, sku.getId().toString(), skuService.getSkuById(sku.getId()));
        } catch (Exception e) {
            log.error("修改商品sku异常，", e);
            return AjaxResult.error("系统繁忙");
        }
        return AjaxResult.success();
    }

    /**
     * 删除商品sku
     *
     * @param id
     * @return
     */
    @ApiOperation("删除商品sku")
    @DeleteMapping("delete")
    @Log(title = "product_sku", funcDesc = "删除商品sku")
    public AjaxResult delete(@ApiParam("skuId") @RequestParam Long id) {
        try {
            skuService.removeById(id);
            esService.deleteDoc(ProductConstants.SKU_INDEX, id.toString());
        } catch (IOException e) {
            log.error("删除商品sku异常，", e);
            return AjaxResult.error("系统繁忙");
        }
        return AjaxResult.success();
    }

    /**
     * 扣减库存
     *
     * @param id
     * @param stock
     * @return
     */
    @ApiOperation("扣减库存")
    @PostMapping("reduceStock")
    @Log(title = "product_sku", funcDesc = "扣减库存")
    public AjaxResult reduceStock(@ApiParam("skuId") @RequestParam Long id, @ApiParam("数量") @RequestParam Integer stock) {
        try {
            skuService.reduceStock(id, stock);
        } catch (Exception e) {
            log.error("扣减库存异常，", e);
            return AjaxResult.error("系统繁忙");
        }
        return AjaxResult.success();
    }

    /**
     * 保存sku图片
     *
     * @param id
     * @param file
     * @return
     */
    @ApiOperation("保存sku图片")
    @PostMapping("saveImage")
    @Log(title = "product_sku", funcDesc = "保存sku图片")
    public AjaxResult saveImage(@ApiParam("skuId") @RequestParam Long id, @ApiParam("图片") @RequestParam MultipartFile file) {
        try {
            skuService.saveImage(id, file);
        } catch (IOException e) {
            log.error("保存sku图片异常，", e);
            return AjaxResult.error("系统繁忙");
        }
        return AjaxResult.success();
    }
}
