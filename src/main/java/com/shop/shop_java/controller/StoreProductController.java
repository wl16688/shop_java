package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreProduct;
import com.shop.shop_java.service.StoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/store/product")
public class StoreProductController {

    @Autowired
    private StoreProductService storeProductService;

    @GetMapping("/list")
    public Result<Page<StoreProduct>> list(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "15") Integer limit,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) Integer type,
                                           @RequestParam(required = false) Integer cateId,
                                           @RequestParam(required = false) Integer productType,
                                           @RequestParam(required = false) String priceMin,
                                           @RequestParam(required = false) String priceMax,
                                           @RequestParam(required = false) String salesMin,
                                           @RequestParam(required = false) String salesMax) {
        Page<StoreProduct> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreProduct> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(q -> q.like(StoreProduct::getStoreName, keyword).or().like(StoreProduct::getKeyword, keyword).or().eq(StoreProduct::getId, keyword));
        }
        if (cateId != null) wrapper.eq(StoreProduct::getCateId, String.valueOf(cateId));
        if (priceMin != null && !priceMin.isEmpty()) wrapper.ge(StoreProduct::getPrice, new java.math.BigDecimal(priceMin));
        if (priceMax != null && !priceMax.isEmpty()) wrapper.le(StoreProduct::getPrice, new java.math.BigDecimal(priceMax));
        if (salesMin != null && !salesMin.isEmpty()) wrapper.ge(StoreProduct::getSales, Integer.parseInt(salesMin));
        if (salesMax != null && !salesMax.isEmpty()) wrapper.le(StoreProduct::getSales, Integer.parseInt(salesMax));
        
        if (type != null) {
            if (type == 6) {
                wrapper.eq(StoreProduct::getIsDel, 1);
            } else {
                wrapper.eq(StoreProduct::getIsDel, 0);
                if (type == 1) {
                    wrapper.eq(StoreProduct::getIsShow, 1).eq(StoreProduct::getIsVerify, 1);
                } else if (type == 2) {
                    wrapper.eq(StoreProduct::getIsShow, 0).eq(StoreProduct::getIsVerify, 1);
                } else if (type == 4) {
                    wrapper.eq(StoreProduct::getIsVerify, 1).and(w -> w.eq(StoreProduct::getIsSold, 1).or().le(StoreProduct::getStock, 0));
                } else if (type == 5) {
                    wrapper.eq(StoreProduct::getIsShow, 1).eq(StoreProduct::getIsVerify, 1).eq(StoreProduct::getIsPolice, 1).gt(StoreProduct::getStock, 0);
                } else if (type == 0) {
                    wrapper.eq(StoreProduct::getIsVerify, 0);
                } else if (type == -1) {
                    wrapper.eq(StoreProduct::getIsVerify, -1);
                } else if (type == -2) {
                    wrapper.eq(StoreProduct::getIsVerify, -2);
                }
            }
        } else {
            wrapper.eq(StoreProduct::getIsDel, 0);
        }
        
        wrapper.orderByDesc(StoreProduct::getId);
        return Result.success(storeProductService.page(pageParam, wrapper));
    }
    
    @GetMapping("/type_header")
    public Result<Map<String, Object>> headerStats(@RequestParam(required = false) String keyword,
                                                   @RequestParam(required = false) Integer cateId,
                                                   @RequestParam(required = false) Integer productType,
                                                   @RequestParam(required = false) String priceMin,
                                                   @RequestParam(required = false) String priceMax,
                                                   @RequestParam(required = false) String salesMin,
                                                   @RequestParam(required = false) String salesMax) {
        Map<String, Object> stats = new HashMap<>();

        // Helper to create base wrapper with common filters
        java.util.function.Supplier<LambdaQueryWrapper<StoreProduct>> baseWrapper = () -> {
            LambdaQueryWrapper<StoreProduct> w = new LambdaQueryWrapper<>();
            if (keyword != null && !keyword.isEmpty()) {
                w.and(q -> q.like(StoreProduct::getStoreName, keyword).or().like(StoreProduct::getKeyword, keyword).or().eq(StoreProduct::getId, keyword));
            }
            if (cateId != null) w.eq(StoreProduct::getCateId, String.valueOf(cateId));
            if (priceMin != null && !priceMin.isEmpty()) w.ge(StoreProduct::getPrice, new java.math.BigDecimal(priceMin));
            if (priceMax != null && !priceMax.isEmpty()) w.le(StoreProduct::getPrice, new java.math.BigDecimal(priceMax));
            if (salesMin != null && !salesMin.isEmpty()) w.ge(StoreProduct::getSales, Integer.parseInt(salesMin));
            if (salesMax != null && !salesMax.isEmpty()) w.le(StoreProduct::getSales, Integer.parseInt(salesMax));
            return w;
        };
        
        // 销售中: is_show = 1, is_del = 0, is_verify = 1
        stats.put("selling", storeProductService.count(baseWrapper.get().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsShow, 1).eq(StoreProduct::getIsVerify, 1)));
        // 仓库中: is_show = 0, is_del = 0, is_verify = 1
        stats.put("warehouse", storeProductService.count(baseWrapper.get().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsShow, 0).eq(StoreProduct::getIsVerify, 1)));
        // 已售罄: is_del = 0, is_verify = 1, (is_sold = 1 or stock <= 0)
        stats.put("soldOut", storeProductService.count(baseWrapper.get().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsVerify, 1).and(w -> w.eq(StoreProduct::getIsSold, 1).or().le(StoreProduct::getStock, 0))));
        // 库存预警: is_show = 1, is_del = 0, is_verify = 1, is_police = 1, stock > 0
        stats.put("alert", storeProductService.count(baseWrapper.get().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsShow, 1).eq(StoreProduct::getIsVerify, 1).eq(StoreProduct::getIsPolice, 1).gt(StoreProduct::getStock, 0)));
        // 回收站: is_del = 1
        stats.put("recycle", storeProductService.count(baseWrapper.get().eq(StoreProduct::getIsDel, 1)));
        // 待审核: is_verify = 0, is_del = 0
        stats.put("pending", storeProductService.count(baseWrapper.get().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsVerify, 0)));
        // 审核未通过: is_verify = -1, is_del = 0
        stats.put("rejected", storeProductService.count(baseWrapper.get().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsVerify, -1)));
        // 强制下架: is_verify = -2, is_del = 0
        stats.put("forced", storeProductService.count(baseWrapper.get().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsVerify, -2)));

        return Result.success(stats);
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody StoreProduct product) {
        if (product.getAddTime() == null) {
            product.setAddTime((int) (System.currentTimeMillis() / 1000));
        }
        return Result.success(storeProductService.saveOrUpdate(product));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        StoreProduct product = storeProductService.getById(id);
        if (product != null) {
            product.setIsDel(1); // 软删除，移入回收站
            return Result.success(storeProductService.updateById(product));
        }
        return Result.success(true);
    }
}
