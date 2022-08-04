package com.qq.order.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qq.common.core.annotation.RepeatCommit;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.log.annotation.Log;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.pojo.SysOrder;
import com.qq.order.server.pojo.OrderQuery;
import com.qq.order.server.service.SysOrderService;
import com.qq.order.server.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 订单
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("order")
@Slf4j
@Api(tags = "订单信息")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController extends BaseController {
    private final SysOrderService orderService;

    private final RedisService redisService;

    /**
     * 查询订单列表
     *
     * @param query
     * @return
     */
    @ApiOperation("查询订单列表")
    @GetMapping("list")
    @Log(title = "order", funcDesc = "查询订单列表")
    public AjaxResult list(OrderQuery query) {
        startPage();
        TableDataInfo dataTable = getDataTable(orderService.list(query), null);
        clearPage();
        return AjaxResult.success(dataTable);
    }

    /**
     * 保存订单
     *
     * @param orderVO
     * @return
     */
    @ApiOperation("保存订单")
    @PostMapping("/saveOrder")
    @Log(title = "order", funcDesc = "保存订单")
    @RepeatCommit
    public AjaxResult saveOrder(@RequestBody OrderVO orderVO) {
        log.info("订单服务开始保存订单");
        return AjaxResult.success(orderService.saveOrder(orderVO));
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    @ApiOperation("订单详情")
    @GetMapping("/detail")
    @Log(title = "order", funcDesc = "订单详情")
    public AjaxResult orderDetailInfo(@ApiParam("用户id") @RequestParam Long orderId) {
        return AjaxResult.success(orderService.getOrderInfo(orderId));
    }

    /**
     * 查询热卖商品
     *
     * @return
     */
    @ApiOperation("查询热卖商品")
    @GetMapping("/hotSales")
    @Log(title = "order", funcDesc = "查询热卖商品")
    public AjaxResult hotSales() {
        return AjaxResult.success(redisService.getCacheList(CacheConstants.HOT_SALE_KEY));
    }

    /**
     * 查询订单各种状态数量
     *
     * @param userId
     * @return
     */
    @ApiOperation("查询订单各种状态数量")
    @GetMapping("/getStatusCount")
    @Log(title = "order", funcDesc = "查询订单各种状态数量")
    public AjaxResult getStatusCount(@ApiParam("用户id") @RequestParam("userId") Long userId) {
        return AjaxResult.success(orderService.getStatusCount(userId));
    }

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param orderStatus
     * @return
     */
    @ApiOperation("修改订单状态")
    @GetMapping("/updateOrderStatus")
    @Log(title = "order", funcDesc = "修改订单状态")
    public AjaxResult updateOrderStatus(@ApiParam("订单id") @RequestParam("orderId") Long orderId,
                                        @ApiParam("订单状态") @RequestParam("orderStatus") Integer orderStatus) {
        orderService.updateOrderStatus(orderId, orderStatus);
        return AjaxResult.success();
    }

    /**
     * 删除订单
     *
     * @param orderId
     * @return
     */
    @ApiOperation("删除订单")
    @GetMapping("/delete")
    @Log(title = "order", funcDesc = "删除订单")
    public AjaxResult updateOrderStatus(@ApiParam("订单id") @RequestParam("orderId") Long orderId) {
        boolean update = orderService.update(new UpdateWrapper<SysOrder>().set("status", 0).eq("id", orderId));
        return update ? AjaxResult.success() : AjaxResult.error("删除失败，订单不存在！");
    }
}
