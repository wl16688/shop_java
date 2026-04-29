package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreCoupon;
import com.shop.shop_java.entity.StoreProductCoupon;
import com.shop.shop_java.service.StoreCouponService;
import com.shop.shop_java.service.StoreProductCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/store/product/coupon")
public class StoreProductCouponController {
    @Autowired
    private StoreProductCouponService productCouponService;
    @Autowired
    private StoreCouponService couponService;

    @GetMapping("/list")
    public Result<List<StoreProductCoupon>> list(@RequestParam Integer product_id) {
        List<StoreProductCoupon> list = productCouponService.list(new LambdaQueryWrapper<StoreProductCoupon>()
                .eq(StoreProductCoupon::getProductId, product_id)
                .orderByDesc(StoreProductCoupon::getId));
        return Result.success(list);
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Map<String, Object> body) {
        Integer productId = toInt(body.get("productId"));
        if (productId == null) productId = toInt(body.get("product_id"));
        if (productId == null) {
            return Result.error(400, "productId不能为空");
        }
        List<Integer> couponIds = toIntList(body.get("couponIds"));
        if (couponIds.isEmpty()) {
            couponIds = toIntList(body.get("coupon_ids"));
        }

        productCouponService.remove(new LambdaQueryWrapper<StoreProductCoupon>()
                .eq(StoreProductCoupon::getProductId, productId));

        if (!couponIds.isEmpty()) {
            int now = (int) (System.currentTimeMillis() / 1000);
            List<StoreProductCoupon> toSave = new ArrayList<>();
            for (Integer cid : couponIds) {
                if (cid == null || cid <= 0) continue;
                StoreProductCoupon c = new StoreProductCoupon();
                c.setProductId(productId);
                c.setIssueCouponId(cid);
                c.setAddTime(now);
                StoreCoupon coupon = couponService.getById(cid);
                c.setTitle(coupon != null ? coupon.getCouponTitle() : "");
                toSave.add(c);
            }
            if (!toSave.isEmpty()) {
                productCouponService.saveBatch(toSave);
            }
        }
        return Result.success(true);
    }

    private Integer toInt(Object v) {
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).intValue();
        try {
            String s = String.valueOf(v).trim();
            if (s.isEmpty()) return null;
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private List<Integer> toIntList(Object v) {
        List<Integer> list = new ArrayList<>();
        if (v == null) return list;
        if (v instanceof List) {
            for (Object o : (List<Object>) v) {
                Integer n = toInt(o);
                if (n != null) list.add(n);
            }
            return list;
        }
        String s = String.valueOf(v).trim();
        if (s.isEmpty()) return list;
        String[] parts = s.split(",");
        for (String p : parts) {
            Integer n = toInt(p);
            if (n != null) list.add(n);
        }
        return list;
    }
}

