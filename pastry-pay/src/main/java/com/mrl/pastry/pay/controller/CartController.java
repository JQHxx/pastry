package com.mrl.pastry.pay.controller;

import com.mrl.pastry.pay.model.params.CartAddParam;
import com.mrl.pastry.pay.model.vo.CartListVO;
import com.mrl.pastry.pay.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Cart controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/16
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("add")
    @ApiOperation("adds or removes item")
    public Integer addItem(@Valid @RequestBody CartAddParam item) {
        return cartService.addItem(item);
    }

    @PostMapping("check/{productId:\\d+}")
    @ApiOperation("checks or unchecks the item")
    public boolean checkItem(@PathVariable Long productId) {
        return cartService.checkItem(productId);
    }

    @GetMapping("list")
    @ApiOperation("gets the cart list")
    public CartListVO getCartList() {
        return cartService.getCartList(false);
    }
}
