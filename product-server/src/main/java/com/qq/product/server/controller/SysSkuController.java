package com.qq.product.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysSku;
import com.qq.product.server.service.SysSkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 查询产品sku列表
     * @param productId
     * @return
     */
    @GetMapping("list")
    @Log(title = "product_sku", funcDesc = "查询产品sku列表")
    public AjaxResult getProductList(@RequestParam Long productId) {
        return AjaxResult.success(skuService.list(productId));
    }

    /**
     * 新增产品sku
     * @param sku
     * @return
     */
    @PutMapping("add")
    @Log(title = "product_sku", funcDesc = "新增产品sku")
    public AjaxResult add(@RequestBody SysSku sku) {
        skuService.save(sku);
        return AjaxResult.success();
    }

    /**
     * 修改产品sku
     * @param sku
     * @return
     */
    @PostMapping("update")
    @Log(title = "product_sku", funcDesc = "修改产品sku")
    public AjaxResult update(@RequestBody SysSku sku) {
        skuService.updateById(sku);
        return AjaxResult.success();
    }

    /**
     * 删除产品sku
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Log(title = "product_sku", funcDesc = "删除产品sku")
    public AjaxResult delete(Long id) {
        skuService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * 批量删除产品
     * @param id
     * @param stock
     * @return
     */
    @PostMapping("reduceStock")
    @Log(title = "product_sku", funcDesc = "扣减库存")
    public AjaxResult reduceStock(@RequestParam Long id, @RequestParam Integer stock) {
        skuService.reduceStock(id, stock);
        return AjaxResult.success();
    }

    /**
     * 保存sku图片
     * @param id
     * @param file
     * @return
     */
    @PostMapping("saveImage")
    @Log(title = "product_sku", funcDesc = "保存sku图片")
    public AjaxResult saveImage(@RequestParam Long id, @RequestParam MultipartFile file) {
        skuService.saveImage(id, file);
        return AjaxResult.success();
    }
}
