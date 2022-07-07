package com.qq.order.server.controller;

import com.qq.common.core.web.controller.BaseController;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.ShoppingCartItem;
import com.qq.common.system.utils.OauthUtils;
import com.qq.order.server.service.ShoppingCartItemService;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartController extends BaseController {
    private final ShoppingCartItemService shoppingCartItemService;

    /**
     * 查询购物车列表
     * @param userId
     * @return
     */
    @GetMapping("list")
    @Log(title = "order_cart", funcDesc = "查询购物车列表")
    public AjaxResult list(@RequestParam Long userId) {
        return AjaxResult.success(shoppingCartItemService.list(userId));
    }

    /**
     * 添加购物车
     * @param shoppingCartItem
     * @return
     */
    @PutMapping("add")
    @Log(title = "order_cart", funcDesc = "添加购物车")
    public AjaxResult addCartItem(@RequestBody ShoppingCartItem shoppingCartItem) {
        shoppingCartItem.setUserId(OauthUtils.getCurrentUserId());
        shoppingCartItemService.addCartItem(shoppingCartItem);
        return AjaxResult.success();
    }

    /**
     * 删除购物车商品
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Log(title = "order_cart", funcDesc = "删除购物车商品")
    public AjaxResult deleteCartItem(@RequestParam Long id) {
        shoppingCartItemService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * 修改购物车商品数量
     * @param id
     * @param num
     * @return
     */
    @PostMapping("update")
    @Log(title = "order_cart", funcDesc = "修改购物车商品数量")
    public AjaxResult updateCartItemNum(@RequestParam Long id, @RequestParam Integer num) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(id);
        shoppingCartItem.setNum(num);
        shoppingCartItemService.updateById(shoppingCartItem);
        return AjaxResult.success();
    }
}
