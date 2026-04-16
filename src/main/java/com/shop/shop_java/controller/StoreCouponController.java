package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreCoupon;
import com.shop.shop_java.service.StoreCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/store/coupon")
public class StoreCouponController {

    @Autowired
    private StoreCouponService storeCouponService;

    @GetMapping("/list")
    public Result<Page<StoreCoupon>> list(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "15") Integer limit,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) Integer status,
                                          @RequestParam(required = false) Integer type) {
        Page<StoreCoupon> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreCoupon> wrapper = new LambdaQueryWrapper<>();
        
        wrapper.eq(StoreCoupon::getIsDel, 0);

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(StoreCoupon::getCouponTitle, keyword);
        }
        if (status != null) {
            wrapper.eq(StoreCoupon::getStatus, status);
        }
        if (type != null) {
            wrapper.eq(StoreCoupon::getType, type);
        }

        wrapper.orderByDesc(StoreCoupon::getId);
        return Result.success(storeCouponService.page(pageParam, wrapper));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody StoreCoupon coupon) {
        if (coupon.getAddTime() == null) {
            coupon.setAddTime((int) (System.currentTimeMillis() / 1000));
        }
        return Result.success(storeCouponService.saveOrUpdate(coupon));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        StoreCoupon coupon = storeCouponService.getById(id);
        if (coupon != null) {
            coupon.setIsDel(1);
            return Result.success(storeCouponService.updateById(coupon));
        }
        return Result.success(true);
    }
}
