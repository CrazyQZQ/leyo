package com.qq.order.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.ShoppingCartItem;
import com.qq.common.system.utils.OauthUtils;
import com.qq.order.server.service.ShoppingCartItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 购物车
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("order/cart")
@Slf4j
@Api(tags = "购物车管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartController extends BaseController {
    private final ShoppingCartItemService shoppingCartItemService;

    /**
     * 查询购物车列表
     *
     * @param userId
     * @return
     */
    @ApiOperation("查询购物车列表")
    @GetMapping("list")
    @Log(title = "order_cart", funcDesc = "查询购物车列表")
    public AjaxResult list(@ApiParam("用户id") @RequestParam Long userId) {
        return AjaxResult.success(shoppingCartItemService.list(userId));
    }

    /**
     * 添加购物车
     *
     * @param shoppingCartItem
     * @return
     */
    @ApiOperation("添加购物车")
    @PutMapping("add")
    @Log(title = "order_cart", funcDesc = "添加购物车")
    public AjaxResult addCartItem(@RequestBody ShoppingCartItem shoppingCartItem) {
        shoppingCartItem.setUserId(OauthUtils.getCurrentUserId());
        shoppingCartItemService.addCartItem(shoppingCartItem);
        return AjaxResult.success();
    }

    /**
     * 删除购物车商品
     *
     * @param id
     * @return
     */
    @ApiOperation("删除购物车商品")
    @DeleteMapping("delete")
    @Log(title = "order_cart", funcDesc = "删除购物车商品")
    public AjaxResult deleteCartItem(@ApiParam("购物车id") @RequestParam Long id) {
        shoppingCartItemService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * 修改购物车商品数量
     *
     * @param id
     * @param num
     * @return
     */
    @ApiOperation("修改购物车商品数量")
    @PostMapping("update")
    @Log(title = "order_cart", funcDesc = "修改购物车商品数量")
    public AjaxResult updateCartItemNum(@ApiParam("购物车id") @RequestParam Long id, @ApiParam("商品数量") @RequestParam Integer num) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(id);
        shoppingCartItem.setNum(num);
        shoppingCartItemService.updateById(shoppingCartItem);
        return AjaxResult.success();
    }
}
