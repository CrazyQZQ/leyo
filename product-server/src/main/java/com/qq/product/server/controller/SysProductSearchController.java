package com.qq.product.server.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.es.constants.IndexConstants;
import com.qq.common.es.service.EsService;
import com.qq.common.es.vo.SearchCommonVO;
import com.qq.common.es.vo.SearchResultVO;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysSku;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 商品信息查询
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/search")
@Slf4j
@Api(tags = "ES商品信息搜索")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductSearchController {
    private final EsService esService;

    /**
     * 搜索sku列表
     *
     * @param vo
     * @return
     */
    @ApiOperation("搜索sku列表")
    @PostMapping("list")
    @Log(title = "product_search", funcDesc = "搜索sku列表")
    public AjaxResult list(@RequestBody SearchCommonVO vo) {
        vo.setIndexName(IndexConstants.INDEX_SKU);
        SearchResultVO<SysSku> search = esService.search(vo, SysSku.class);
        return AjaxResult.success(search);
    }

}
