package com.qq.order.server.controller;

import com.qq.common.core.annotation.RepeatCommit;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.core.web.page.TableDataInfo;
import com.qq.common.log.annotation.Log;
import com.qq.common.redis.service.RedisService;
import com.qq.order.server.pojo.OrderQuery;
import com.qq.order.server.service.SysOrderService;
import com.qq.order.server.vo.OrderVO;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController extends BaseController {
    private final SysOrderService orderService;

    private final RedisService redisService;

    /**
     * 查询订单列表
     * @param query
     * @return
     */
    @GetMapping("list")
    @Log(title = "order", funcDesc = "查询订单列表")
    public TableDataInfo list(OrderQuery query) {
        startPage();
        TableDataInfo dataTable = getDataTable(orderService.list(query));
        clearPage();
        return dataTable;
    }

    /**
     * 保存订单
     * @param orderVO
     * @return
     */
    @PostMapping("/saveOrder")
    @Log(title = "order", funcDesc = "保存订单")
    @RepeatCommit
    public AjaxResult saveOrder(@RequestBody OrderVO orderVO) {
        log.info("订单服务开始保存订单");
        return AjaxResult.success(orderService.saveOrder(orderVO));
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    @Log(title = "order", funcDesc = "订单详情")
    public AjaxResult orderDetailInfo(Long orderId) {
        return AjaxResult.success(orderService.getOrderInfo(orderId));
    }

    /**
     * 查询热卖商品
     * @return
     */
    @GetMapping("/hotSales")
    @Log(title = "order", funcDesc = "查询热卖商品")
    public AjaxResult hotSales() {
        return AjaxResult.success(redisService.getCacheList(CacheConstants.HOT_SALE_KEY));
    }

    /**
     * 查询订单各种状态数量
     * @param userId
     * @return
     */
    @GetMapping("/getStatusCount")
    @Log(title = "order", funcDesc = "查询订单各种状态数量")
    public AjaxResult getStatusCount(@RequestParam("userId") Long userId) {
        return AjaxResult.success(orderService.getStatusCount(userId));
    }
}
