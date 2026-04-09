package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.entity.StoreProduct;
import com.shop.shop_java.mapper.StoreProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/extend/product_product_list")
public class ProductListController {

    @Autowired
    private StoreProductMapper storeProductMapper;

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String type) {
        
        Page<StoreProduct> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreProduct> queryWrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(StoreProduct::getStoreName, keyword);
        }
        
        if (type != null && !type.isEmpty()) {
            if ("1".equals(type)) {
                queryWrapper.eq(StoreProduct::getIsShow, 1);
            } else if ("2".equals(type)) {
                queryWrapper.eq(StoreProduct::getIsShow, 0);
            }
        }
        
        queryWrapper.orderByDesc(StoreProduct::getSort).orderByDesc(StoreProduct::getId);
        storeProductMapper.selectPage(pageParam, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("records", pageParam.getRecords());
        data.put("total", pageParam.getTotal());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }
}
