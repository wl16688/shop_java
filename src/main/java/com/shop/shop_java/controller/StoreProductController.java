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

/**
 * 商品管理控制器
 * 提供后台商品列表、分类状态统计及操作等功能
 */
@RestController
@RequestMapping("/api/admin/store/product")
public class StoreProductController {

    @Autowired
    private StoreProductService storeProductService;

    // 用于统计接口的1分钟缓存结构
    private static class CacheData {
        Map<String, Object> data;
        long expireTime;
        CacheData(Map<String, Object> data, long expireTime) {
            this.data = data;
            this.expireTime = expireTime;
        }
    }
    // 缓存容器
    private final java.util.concurrent.ConcurrentHashMap<String, CacheData> statsCache = new java.util.concurrent.ConcurrentHashMap<>();

    /**
     * 获取商品分页列表
     * 核心逻辑：基于前端传入的 `type` 参数，结合多重字段状态（如是否删除、审核状态、库存等）返回对应分类数据。
     *
     * @param page        当前页码
     * @param limit       每页条数
     * @param keyword     商品搜索关键词（名称/关键字/ID）
     * @param type        商品状态分类页签（1=销售中, 2=仓库中, 4=已售罄, 5=库存预警, 6=回收站, 0=待审核, -1=审核未通过, -2=强制下架）
     * @param cateId      商品分类ID
     * @param productType 商品类型
     * @param priceMin    最低售价
     * @param priceMax    最高售价
     * @param salesMin    最低销量
     * @param salesMax    最高销量
     * @return 分页列表数据
     */
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
        
        // 根据状态类型(type)动态追加底层数据库状态字段的过滤条件
        // 实现了与 PHP 原版 100% 相同的组合查询逻辑
        if (type != null) {
            if (type == 6) {
                // 回收站：仅过滤被删除的 (is_del = 1)
                wrapper.eq(StoreProduct::getIsDel, 1);
            } else {
                // 非回收站：过滤未被删除的
                wrapper.eq(StoreProduct::getIsDel, 0);
                if (type == 1) {
                    // 销售中：已上架 (is_show=1) 且 审核通过 (is_verify=1)
                    wrapper.eq(StoreProduct::getIsShow, 1).eq(StoreProduct::getIsVerify, 1);
                } else if (type == 2) {
                    // 仓库中(下架)：已下架 (is_show=0) 且 审核通过
                    wrapper.eq(StoreProduct::getIsShow, 0).eq(StoreProduct::getIsVerify, 1);
                } else if (type == 4) {
                    // 已售罄：审核通过 且 (标记售罄(is_sold=1) 或 真实库存<=0)
                    wrapper.eq(StoreProduct::getIsVerify, 1).and(w -> w.eq(StoreProduct::getIsSold, 1).or().le(StoreProduct::getStock, 0));
                } else if (type == 5) {
                    // 库存预警：已上架、审核通过、触发预警标记(is_police=1) 且 真实库存>0
                    wrapper.eq(StoreProduct::getIsShow, 1).eq(StoreProduct::getIsVerify, 1).eq(StoreProduct::getIsPolice, 1).gt(StoreProduct::getStock, 0);
                } else if (type == 0) {
                    // 待审核：审核状态为待定(is_verify=0)
                    wrapper.eq(StoreProduct::getIsVerify, 0);
                } else if (type == -1) {
                    // 审核未通过
                    wrapper.eq(StoreProduct::getIsVerify, -1);
                } else if (type == -2) {
                    // 强制下架
                    wrapper.eq(StoreProduct::getIsVerify, -2);
                }
            }
        } else {
            // 默认查未被删除的商品
            wrapper.eq(StoreProduct::getIsDel, 0);
        }
        
        wrapper.orderByDesc(StoreProduct::getId);
        return Result.success(storeProductService.page(pageParam, wrapper));
    }
    
    /**
     * 获取商品各分类(Tab)状态数量统计
     * 针对所有 `type` 的查询，提取公共搜索条件以保证筛选时所有页签数值准确联动。
     *
     * @return 包含各分类对应数量的 Map (如：selling: 10, warehouse: 5...)
     */
    @GetMapping("/status_statistics")
    public Result<Map<String, Object>> statusStatistics(@RequestParam(required = false) String keyword,
                                                        @RequestParam(required = false) Integer cateId,
                                                        @RequestParam(required = false) Integer productType,
                                                        @RequestParam(required = false) String priceMin,
                                                        @RequestParam(required = false) String priceMax,
                                                        @RequestParam(required = false) String salesMin,
                                                        @RequestParam(required = false) String salesMax) {
        
        // 缓存键：组合所有查询参数
        String cacheKey = String.format("%s_%s_%s_%s_%s_%s_%s", keyword, cateId, productType, priceMin, priceMax, salesMin, salesMax);
        CacheData cache = statsCache.get(cacheKey);
        
        // 1分钟内查询使用缓存
        if (cache != null && System.currentTimeMillis() < cache.expireTime) {
            return Result.success(cache.data);
        }

        Map<String, Object> stats = new HashMap<>();

        // 提取公共过滤条件构造器 (公用搜索条件：关键词、分类、价格范围、销量范围等)
        // 这样在计算各个状态数量时，都会自动叠加这些搜索限制
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

        // 存入缓存，有效期1分钟
        statsCache.put(cacheKey, new CacheData(stats, System.currentTimeMillis() + 60 * 1000));

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
