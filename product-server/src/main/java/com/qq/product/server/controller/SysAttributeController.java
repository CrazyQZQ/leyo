package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysAttribute;
import com.qq.product.server.service.SysAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 商品属性管理
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/attribute")
@Slf4j
@Api(tags = "商品属性管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysAttributeController extends BaseController {
    private final SysAttributeService attributeService;

    /**
     * 查询商品属性列表
     *
     * @return
     */
    @ApiOperation("查询商品属性列表")
    @GetMapping("list")
    @Log(title = "product_attribute", funcDesc = "查询商品属性列表")
    public AjaxResult getProductList() {
        return AjaxResult.success(attributeService.list());
    }

    /**
     * 新增商品sku
     *
     * @param attribute
     * @return
     */
    @ApiOperation("新增商品属性")
    @PutMapping("add")
    @Log(title = "product_attribute", funcDesc = "新增商品属性")
    public AjaxResult add(@RequestBody SysAttribute attribute) {
        attributeService.add(attribute);
        return AjaxResult.success();
    }

    /**
     * 修改商品属性
     *
     * @param attribute
     * @return
     */
    @ApiOperation("修改商品属性")
    @PostMapping("update")
    @Log(title = "product_attribute", funcDesc = "修改商品属性")
    public AjaxResult update(@RequestBody SysAttribute attribute) {
        attributeService.update(attribute);
        return AjaxResult.success();
    }

    /**
     * 删除商品属性
     *
     * @param id
     * @return
     */
    @ApiOperation("删除商品属性")
    @DeleteMapping("delete")
    @Log(title = "product_attribute", funcDesc = "删除商品属性")
    public AjaxResult delete(@ApiParam("商品属性id") Long id) {
        attributeService.delete(id);
        return AjaxResult.success();
    }
}
