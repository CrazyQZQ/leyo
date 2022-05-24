package com.qq.product.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysAttribute;
import com.qq.product.server.service.SysAttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/attribute")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysAttributeController extends BaseController {
    private final SysAttributeService attributeService;

    /**
     * 查询产品属性列表
     * @return
     */
    @GetMapping("list")
    @Log(title = "product_attribute", funcDesc = "查询产品属性列表")
    public AjaxResult getProductList() {
        return AjaxResult.success(attributeService.list());
    }

    /**
     * 新增产品sku
     * @param attribute
     * @return
     */
    @PutMapping("add")
    @Log(title = "product_attribute", funcDesc = "新增产品属性")
    public AjaxResult add(@RequestBody SysAttribute attribute) {
        attributeService.add(attribute);
        return AjaxResult.success();
    }

    /**
     * 修改产品属性
     * @param attribute
     * @return
     */
    @PostMapping("update")
    @Log(title = "product_attribute", funcDesc = "修改产品属性")
    public AjaxResult update(@RequestBody SysAttribute attribute) {
        attributeService.update(attribute);
        return AjaxResult.success();
    }

    /**
     * 删除产品属性
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Log(title = "product_attribute", funcDesc = "删除产品属性")
    public AjaxResult delete(Long id) {
        attributeService.delete(id);
        return AjaxResult.success();
    }
}
