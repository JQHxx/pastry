package com.mrl.pastry.pay.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrl.pastry.pay.model.params.OrderCallback;
import com.mrl.pastry.pay.model.params.OrderSubmitParam;
import com.mrl.pastry.pay.model.vo.OrderConfirmVO;
import com.mrl.pastry.pay.model.vo.OrderListVO;
import com.mrl.pastry.pay.model.vo.PaySignature;
import com.mrl.pastry.pay.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Order controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/16
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("confirm")
    @ApiOperation("Gets order confirmation")
    public OrderConfirmVO confirm() {
        return orderService.confirm();
    }

    @GetMapping("list")
    @ApiOperation("Gets order history")
    public IPage<OrderListVO> getPage(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                      @RequestParam(value = "limit", required = false) Long limit) {
        return orderService.getOrderList(pageable, limit);
    }

    @PostMapping("pay")
    @ApiOperation("Saves order and gets payment signature")
    public PaySignature pay(@Valid @RequestBody OrderSubmitParam submitParam) {
        return orderService.pay(submitParam);
    }

    @GetMapping("continueToPay")
    @ApiOperation("Continue to pay for the order")
    public PaySignature pay(@RequestParam("orderSn") String orderSn) {
        return orderService.pay(orderSn);
    }

    @PostMapping("cancel/{orderSn}")
    @ApiOperation("Cancels the order")
    public String cancel(@PathVariable("orderSn") String orderSn) {
        // 手动取消
        orderService.cancelOrder(orderSn, false);
        return "SUCCESS";
    }

    @PostMapping("callback")
    @ApiOperation("payment callback")
    public String callback(@RequestBody OrderCallback callback) {
        return orderService.process(callback);
    }
}
