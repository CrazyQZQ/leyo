package com.qq.product.server.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysSkuEvaluation;
import com.qq.product.server.service.SysSkuEvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 商品评价
 * @Author QinQiang
 * @Date 2022/8/4
 **/
@RestController
@RequestMapping("product/evaluation")
@Slf4j
@Api(tags = "商品评价")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSkuEvaluationController extends BaseController<SysSkuEvaluation> {

    private final SysSkuEvaluationService skuEvaluationService;

    /**
     * 查询用户商品评价
     *
     * @param
     * @return
     */
    @ApiOperation("查询用户商品评价")
    @PostMapping("list")
    @Log(title = "product_evaluation", funcDesc = "查询用户商品评价")
    public AjaxResult list(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long orderId,
                           @RequestParam(required = false) Long orderDetailId, @RequestParam(required = false) Long skuId) {
        startPage();
        List<SysSkuEvaluation> list = skuEvaluationService.list(userId, orderId, orderDetailId, skuId);
        TableDataInfo dataTable = getDataTable(list, null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * 新增商品评价
     *
     * @param list
     * @return
     */
    @ApiOperation("新增商品评价")
    @PostMapping("add")
    @Log(title = "product_evaluation", funcDesc = "新增商品评价")
    public AjaxResult add(@RequestBody List<SysSkuEvaluation> list) {
        skuEvaluationService.saveBatch(list);
        return AjaxResult.success();
    }

    /**
     * 修改商品评价
     *
     * @param sysSkuEvaluation
     * @return
     */
    @ApiOperation("修改商品评价")
    @PutMapping("update")
    @Log(title = "product_evaluation", funcDesc = "修改商品评价")
    public AjaxResult update(@RequestBody SysSkuEvaluation sysSkuEvaluation) {
        if(ObjectUtil.isEmpty(sysSkuEvaluation.getId())){
            return AjaxResult.error("id不能为空！");
        }
        sysSkuEvaluation.setEvaluateStatus(1);
        boolean update = skuEvaluationService.update(sysSkuEvaluation, new QueryWrapper<SysSkuEvaluation>().eq("id", sysSkuEvaluation.getId()));
        return update ? AjaxResult.success() : AjaxResult.error("商品评价失败");
    }

    /**
     * 删除商品评价
     *
     * @param id
     * @return
     */
    @ApiOperation("删除商品评价")
    @DeleteMapping("delete")
    @Log(title = "product_evaluation", funcDesc = "删除商品评价")
    public AjaxResult delete(@RequestParam Integer id) {
        boolean update = skuEvaluationService.update(new UpdateWrapper<SysSkuEvaluation>().set("status", 0).eq("id", id));
        return update ? AjaxResult.success() : AjaxResult.error("删除商品评价失败");
    }
}
