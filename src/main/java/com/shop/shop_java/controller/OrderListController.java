package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.entity.StoreOrder;
import com.shop.shop_java.mapper.StoreOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/order")
public class OrderListController {

    @Autowired
    private StoreOrderMapper storeOrderMapper;

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String status) {
        
        Page<StoreOrder> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreOrder> queryWrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(StoreOrder::getOrderId, keyword);
        }
        
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(StoreOrder::getStatus, Integer.parseInt(status));
        }
        
        queryWrapper.orderByDesc(StoreOrder::getAddTime);
        storeOrderMapper.selectPage(pageParam, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("list", pageParam.getRecords()); // keep "list" as expected by frontend
        data.put("total", pageParam.getTotal());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }
}
