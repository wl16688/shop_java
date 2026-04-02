package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreOrder;
import com.shop.shop_java.service.StoreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/store/order")
public class StoreOrderController {

    @Autowired
    private StoreOrderService storeOrderService;

    @GetMapping("/list")
    public Result<Page<StoreOrder>> list(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "15") Integer limit,
                                         @RequestParam(required = false) String orderId,
                                         @RequestParam(required = false) Integer status) {
        Page<StoreOrder> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreOrder> wrapper = new LambdaQueryWrapper<>();
        
        if (orderId != null && !orderId.isEmpty()) {
            wrapper.eq(StoreOrder::getOrderId, orderId).or().eq(StoreOrder::getTradeNo, orderId);
        }
        if (status != null) {
            wrapper.eq(StoreOrder::getStatus, status);
        }
        wrapper.orderByDesc(StoreOrder::getId);
        return Result.success(storeOrderService.page(pageParam, wrapper));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody StoreOrder order) {
        return Result.success(storeOrderService.saveOrUpdate(order));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(storeOrderService.removeById(id));
    }
}
