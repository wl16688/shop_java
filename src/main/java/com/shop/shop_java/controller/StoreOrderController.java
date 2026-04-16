package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreOrder;
import com.shop.shop_java.service.StoreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/store/order")
public class StoreOrderController {

    @Autowired
    private StoreOrderService storeOrderService;

    @GetMapping("/list")
    public Result<Page<StoreOrder>> list(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "15") Integer limit,
                                         @RequestParam(required = false) String orderId,
                                         @RequestParam(required = false) Integer status,
                                         @RequestParam(required = false) String realName,
                                         @RequestParam(required = false) String userPhone,
                                         @RequestParam(required = false) Integer payType,
                                         @RequestParam(required = false) Integer type,
                                         @RequestParam(required = false) Integer refundStatus) {
        Page<StoreOrder> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreOrder> wrapper = new LambdaQueryWrapper<>();
        
        wrapper.eq(StoreOrder::getIsDel, 0);

        if (orderId != null && !orderId.isEmpty()) {
            wrapper.and(w -> w.eq(StoreOrder::getOrderId, orderId).or().like(StoreOrder::getTradeNo, orderId));
        }
        if (status != null) {
            wrapper.eq(StoreOrder::getStatus, status);
        }
        
        // 新增的订单高级搜索条件
        if (realName != null && !realName.isEmpty()) {
            wrapper.like(StoreOrder::getRealName, realName);
        }
        if (userPhone != null && !userPhone.isEmpty()) {
            wrapper.like(StoreOrder::getUserPhone, userPhone);
        }
        if (type != null) {
            wrapper.eq(StoreOrder::getType, type);
        }
        
        wrapper.orderByDesc(StoreOrder::getId);
        return Result.success(storeOrderService.page(pageParam, wrapper));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody StoreOrder order) {
        return Result.success(storeOrderService.saveOrUpdate(order));
    }

    @PostMapping("/deliver/{id}")
    public Result<Boolean> deliver(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        StoreOrder order = storeOrderService.getById(id);
        if (order == null) {
            return Result.error(404, "订单不存在");
        }
        String deliveryName = payload.get("deliveryName");
        String deliveryId = payload.get("deliveryId");
        
        order.setDeliveryName(deliveryName);
        order.setDeliveryId(deliveryId);
        order.setStatus(1); // 1 = 待收货 / 已发货
        
        return Result.success(storeOrderService.updateById(order));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        StoreOrder order = storeOrderService.getById(id);
        if (order != null) {
            order.setIsDel(1);
            return Result.success(storeOrderService.updateById(order));
        }
        return Result.success(true);
    }
}
