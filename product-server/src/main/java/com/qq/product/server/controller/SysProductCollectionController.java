package com.qq.product.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysProduct;
import com.qq.common.system.pojo.SysProductCollection;
import com.qq.product.server.service.SysProductCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 商品收藏
 * @Author QinQiang
 * @Date 2022/8/4
 **/
@RestController
@RequestMapping("product/collection")
@Slf4j
@Api(tags = "商品收藏")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductCollectionController  extends BaseController<SysProduct> {
    private final SysProductCollectionService productCollectionService;

    /**
     * 查询收藏商品
     *
     * @param query
     * @return
     */
    @ApiOperation("查询收藏商品")
    @PostMapping("list")
    @Log(title = "product_collection", funcDesc = "查询收藏商品")
    public AjaxResult getProductList(@RequestBody BaseQuery query) {
        startPage();
        List<SysProduct> productList = productCollectionService.listProductCollections(query);
        TableDataInfo dataTable = getDataTable(productList, null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * 收藏商品
     *
     * @param productCollection
     * @return
     */
    @ApiOperation("收藏商品")
    @PostMapping("collect")
    @Log(title = "product_collection", funcDesc = "收藏商品")
    public AjaxResult collect(@RequestBody SysProductCollection productCollection) {
        productCollectionService.collect(productCollection);
        return AjaxResult.success();
    }

    /**
     * 取消收藏商品
     *
     * @param id
     * @return
     */
    @ApiOperation("取消收藏商品")
    @DeleteMapping("unCollect")
    @Log(title = "product_collection", funcDesc = "取消收藏商品")
    public AjaxResult unCollect(@RequestParam Long id) {
        productCollectionService.update(new LambdaUpdateWrapper<SysProductCollection>()
                .set(SysProductCollection::getStatus, 0)
                .eq(SysProductCollection::getId, id));
        return AjaxResult.success();
    }
}
