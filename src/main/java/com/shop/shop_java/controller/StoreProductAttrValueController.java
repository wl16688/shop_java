package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreProductAttrValue;
import com.shop.shop_java.service.StoreProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/store/product/attr_value")
public class StoreProductAttrValueController {
    @Autowired
    private StoreProductAttrValueService attrValueService;

    @GetMapping("/list")
    public Result<Page<StoreProductAttrValue>> list(@RequestParam Integer product_id,
                                                    @RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer limit) {
        LambdaQueryWrapper<StoreProductAttrValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StoreProductAttrValue::getProductId, product_id);
        wrapper.eq(StoreProductAttrValue::getType, 0);
        wrapper.orderByDesc(StoreProductAttrValue::getId);
        Page<StoreProductAttrValue> pageParam = new Page<>(page, limit);
        return Result.success(attrValueService.page(pageParam, wrapper));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody java.util.List<StoreProductAttrValue> attrValues) {
        if (attrValues != null && !attrValues.isEmpty()) {
            attrValueService.saveOrUpdateBatch(attrValues);
        }
        return Result.success(true);
    }
}
