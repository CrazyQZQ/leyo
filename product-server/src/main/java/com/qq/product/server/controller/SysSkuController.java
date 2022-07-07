package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.es.service.EsService;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysSku;
import com.qq.product.server.constants.ProductConstants;
import com.qq.product.server.service.SysSkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/sku")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSkuController extends BaseController {
    private final SysSkuService skuService;
    private final EsService esService;

    /**
     * 查询产品sku列表
     *
     * @param productId
     * @return
     */
    @GetMapping("list")
    @Log(title = "product_sku", funcDesc = "查询产品sku列表")
    public AjaxResult getSkuList(@RequestParam Long productId) {
        return AjaxResult.success(skuService.list(productId));
    }

    /**
     * id查询产品sku
     *
     * @param id
     * @return
     */
    @GetMapping("getById")
    @Log(title = "product_sku", funcDesc = "id查询产品sku")
    public AjaxResult getSkuById(@RequestParam Long id) {
        return AjaxResult.success(skuService.getSkuById(id));
    }

    /**
     * 新增产品sku
     *
     * @param sku
     * @return
     */
    @PutMapping("add")
    @Log(title = "product_sku", funcDesc = "新增产品sku")
    public AjaxResult add(@RequestBody SysSku sku) {
        try {
            skuService.save(sku);
            esService.addDoc(ProductConstants.SKU_INDEX, sku.getId().toString(), skuService.getSkuById(sku.getId()));
        } catch (Exception e) {
            log.error("新增产品sku异常，", e);
            return AjaxResult.error("系统繁忙");
        }
        return AjaxResult.success();
    }

    /**
     * 修改产品sku
     *
     * @param sku
     * @return
     */
    @PostMapping("update")
    @Log(title = "product_sku", funcDesc = "修改产品sku")
    public AjaxResult update(@RequestBody SysSku sku) {
        try {
            skuService.updateById(sku);
            esService.updateDoc(ProductConstants.SKU_INDEX, sku.getId().toString(), skuService.getSkuById(sku.getId()));
        } catch (Exception e) {
            log.error("修改产品sku异常，", e);
            return AjaxResult.error("系统繁忙");
        }
        return AjaxResult.success();
    }

    /**
     * 删除产品sku
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Log(title = "product_sku", funcDesc = "删除产品sku")
    public AjaxResult delete(Long id) {
        try {
            skuService.removeById(id);
            esService.deleteDoc(ProductConstants.SKU_INDEX, id.toString());
        } catch (IOException e) {
            log.error("删除产品sku异常，", e);
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
    @PostMapping("reduceStock")
    @Log(title = "product_sku", funcDesc = "扣减库存")
    public AjaxResult reduceStock(@RequestParam Long id, @RequestParam Integer stock) {
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
    @PostMapping("saveImage")
    @Log(title = "product_sku", funcDesc = "保存sku图片")
    public AjaxResult saveImage(@RequestParam Long id, @RequestParam MultipartFile file) {
        try {
            skuService.saveImage(id, file);
        } catch (IOException e) {
            log.error("保存sku图片异常，", e);
            return AjaxResult.error("系统繁忙");
        }
        return AjaxResult.success();
    }
}
