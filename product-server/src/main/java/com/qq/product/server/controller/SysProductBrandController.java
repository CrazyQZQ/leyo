package com.qq.product.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysBrand;
import com.qq.common.system.pojo.SysProduct;
import com.qq.product.server.service.SysBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("product/brand")
@Slf4j
@Api(tags = "商品品牌管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductBrandController extends BaseController {
    private final SysBrandService sysBrandService;

    /**
     * 查询品牌树
     *
     * @return
     */
    @ApiOperation("查询品牌树")
    @GetMapping("tree")
    @Log(title = "product_brand", funcDesc = "查询品牌树")
    public AjaxResult getProductBrandTree() {
        return AjaxResult.success(sysBrandService.queryTreeList());
    }

    /**
     * 查询品牌列表
     *
     * @param query
     * @return
     */
    @ApiOperation("查询品牌列表")
    @GetMapping("list")
    @Log(title = "product_brand", funcDesc = "查询品牌列表")
    public AjaxResult getProductBrandList(BaseQuery query) {
        startPage();
        List<SysBrand> brands = sysBrandService.list(query);
        TableDataInfo dataTable = getDataTable(brands, null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * 查询品牌详情
     *
     * @param id
     * @return
     */
    @ApiOperation("查询品牌详情")
    @GetMapping("info")
    @Log(title = "product_brand", funcDesc = "查询品牌详情")
    public AjaxResult getBrandById(@ApiParam("品牌id") Long id) {
        return AjaxResult.success(sysBrandService.getById(id));
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @return
     */
    @ApiOperation("新增品牌")
    @PutMapping("add")
    @Log(title = "product_brand", funcDesc = "新增品牌")
    public AjaxResult add(SysBrand brand) {
        try {
            sysBrandService.addBrand(brand);
        } catch (Exception e) {
            log.error("新增品牌失败", e);
            return AjaxResult.error("新增品牌失败!");
        }
        return AjaxResult.success();
    }

    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    @ApiOperation("修改品牌")
    @PostMapping("update")
    @Log(title = "product_brand", funcDesc = "修改品牌")
    public AjaxResult update(SysBrand brand) {
        try {
            sysBrandService.updateBrand(brand);
        } catch (Exception e) {
            log.error("修改品牌失败", e);
            return AjaxResult.error("修改商品失败!");
        }
        return AjaxResult.success();
    }

    /**
     * 删除品牌
     *
     * @param id
     * @return
     */
    @ApiOperation("删除品牌")
    @DeleteMapping("delete")
    @Log(title = "product_brand", funcDesc = "删除品牌")
    public AjaxResult delete(@ApiParam("品牌id") Long id) {
        try {
            sysBrandService.deleteBrand(id);
        } catch (Exception e) {
            log.error("删除品牌失败", e);
            return AjaxResult.error("删除品牌失败!");
        }
        return AjaxResult.success();
    }
}
