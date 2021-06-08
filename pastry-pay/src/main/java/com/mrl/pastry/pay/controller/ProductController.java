package com.mrl.pastry.pay.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrl.pastry.common.support.EnumConverter;
import com.mrl.pastry.pay.model.enums.ProductType;
import com.mrl.pastry.pay.model.vo.ProductVO;
import com.mrl.pastry.pay.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Product controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/17
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("page")
    @ApiOperation("Gets a page of products")
    public IPage<ProductVO> getProductPage(@PageableDefault(sort = {"priority"}, direction = DESC) Pageable pageable,
                                           @RequestParam(name = "type", defaultValue = "0", required = false) Integer type) {
        ProductType productType = EnumConverter.valueToEnum(type, ProductType.class);
        return productService.getProductPage(productType, pageable);
    }
}
