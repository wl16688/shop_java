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
                                           @RequestParam(required = false) Integer isShow,
                                           @RequestParam(required = false) Integer cateId,
                                           @RequestParam(required = false) Integer type,
                                           @RequestParam(required = false) String priceMin,
                                           @RequestParam(required = false) String priceMax,
                                           @RequestParam(required = false) String salesMin,
                                           @RequestParam(required = false) String salesMax) {
        Page<StoreProduct> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreProduct> wrapper = new LambdaQueryWrapper<>();
        
        wrapper.eq(StoreProduct::getIsDel, 0);

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(StoreProduct::getStoreName, keyword).or().like(StoreProduct::getKeyword, keyword).or().eq(StoreProduct::getId, keyword));
        }
        if (isShow != null) {
            wrapper.eq(StoreProduct::getIsShow, isShow);
        }
        
        // 补充的高级搜索条件
        if (priceMin != null && !priceMin.isEmpty()) {
            wrapper.ge(StoreProduct::getPrice, priceMin);
        }
        if (priceMax != null && !priceMax.isEmpty()) {
            wrapper.le(StoreProduct::getPrice, priceMax);
        }
        if (salesMin != null && !salesMin.isEmpty()) {
            wrapper.ge(StoreProduct::getSales, salesMin);
        }
        if (salesMax != null && !salesMax.isEmpty()) {
            wrapper.le(StoreProduct::getSales, salesMax);
        }
        
        wrapper.orderByDesc(StoreProduct::getId);
        return Result.success(storeProductService.page(pageParam, wrapper));
    }
    
    @GetMapping("/headerStats")
    public Result<Map<String, Long>> headerStats() {
        Map<String, Long> stats = new HashMap<>();
        
        // 出售中
        stats.put("selling", storeProductService.count(new LambdaQueryWrapper<StoreProduct>().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsShow, 1)));
        // 仓库中 (下架)
        stats.put("warehouse", storeProductService.count(new LambdaQueryWrapper<StoreProduct>().eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsShow, 0)));
        // 已售罄
        stats.put("soldOut", storeProductService.count(new LambdaQueryWrapper<StoreProduct>().eq(StoreProduct::getIsDel, 0).le(StoreProduct::getStock, 0)));
        // 回收站
        stats.put("recycle", storeProductService.count(new LambdaQueryWrapper<StoreProduct>().eq(StoreProduct::getIsDel, 1)));
        
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
