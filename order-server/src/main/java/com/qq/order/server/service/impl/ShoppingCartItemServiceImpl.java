package com.qq.order.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.ShoppingCartItem;
import com.qq.common.system.pojo.SysSku;
import com.qq.order.server.service.ShoppingCartItemService;
import com.qq.order.server.mapper.ShoppingCartItemMapper;
import com.qq.order.server.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【shopping_cart_item(购物车清单)】的数据库操作Service实现
 * @createDate 2022-06-02 09:32:58
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartItemServiceImpl extends ServiceImpl<ShoppingCartItemMapper, ShoppingCartItem>
        implements ShoppingCartItemService {

    private final SkuService skuService;

    /**
     * 查询购物车列表
     * @param userId
     * @return
     */
    @Override
    public List<ShoppingCartItem> list(Long userId) {
        List<ShoppingCartItem> cartItems = this.baseMapper.selectList(new QueryWrapper<ShoppingCartItem>().eq("user_id", userId));
        for (ShoppingCartItem cartItem : cartItems) {
            AjaxResult ajaxResult = skuService.getSkuById(cartItem.getSkuId());
            if (ajaxResult.getCode() == HttpStatus.OK.value()) {
                SysSku sku = BeanUtil.mapToBean((Map<String, Object>) ajaxResult.getData(), SysSku.class, true, null);
                cartItem.setSku(sku);
            } else {
                throw new ServiceException(ajaxResult.getMsg());
            }
        }
        return cartItems;
    }

    /**
     * 添加购物车
     * @param shoppingCartItem
     */
    @Override
    public void addCartItem(ShoppingCartItem shoppingCartItem) {
        Long skuId = shoppingCartItem.getSkuId();
        if (skuId == null) {
            throw new ServiceException("商品不能为空");
        }
        QueryWrapper<ShoppingCartItem> queryWrapper = new QueryWrapper<ShoppingCartItem>()
                .select("id", "num")
                .eq("user_id", shoppingCartItem.getUserId())
                .eq("sku_id", skuId);
        ShoppingCartItem cartItem = this.baseMapper.selectOne(queryWrapper);
        if (cartItem != null) {
            cartItem.setNum(cartItem.getNum() + shoppingCartItem.getNum());
            this.baseMapper.updateById(cartItem);
        } else {
            this.baseMapper.insert(shoppingCartItem);
        }
    }
}




