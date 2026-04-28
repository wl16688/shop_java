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
     * @param storeName   商品搜索关键词（对应 PHP 的 store_name，结合 field_key 使用）
     * @param fieldKey    搜索类型：store_name(商品名称), product_id(商品ID), all(全部)
     * @param type        商品状态分类页签（1=销售中, 2=仓库中, 4=已售罄, 5=库存预警, 6=回收站, 0=待审核, -1=审核未通过, -2=强制下架）
     * @param cateId      商品分类ID
     * @param productType 商品类型
     * @param priceRange  售价区间，格式如 "10-100" 或 "10-" 或 "-100"
     * @param salesRange  销量区间，格式如 "10-100"
     * @param stockRange  库存区间，格式如 "10-100"
     * @param supplierId  供应商ID
     * @return 分页列表数据
     */
    @GetMapping("/list")
    public Result<Page<StoreProduct>> list(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "15") Integer limit,
                                           @RequestParam(required = false, name = "store_name") String storeName,
                                           @RequestParam(required = false, name = "field_key") String fieldKey,
                                           @RequestParam(required = false) Integer type,
                                           @RequestParam(required = false, name = "cate_id") Integer cateId,
                                           @RequestParam(required = false, name = "product_type") Integer productType,
                                           @RequestParam(required = false, name = "price_range") String priceRange,
                                           @RequestParam(required = false, name = "sales_range") String salesRange,
                                           @RequestParam(required = false, name = "stock_range") String stockRange,
                                           @RequestParam(required = false, name = "supplier_id") Integer supplierId) {
        Page<StoreProduct> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreProduct> wrapper = buildBaseQuery(storeName, fieldKey, cateId, productType, priceRange, salesRange, stockRange, supplierId);
        
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
            // 如果 type 为空，默认表现和 PHP 完全对齐，默认查询 "出售中" (type=1) 的逻辑
            wrapper.eq(StoreProduct::getIsDel, 0).eq(StoreProduct::getIsShow, 1).eq(StoreProduct::getIsVerify, 1);
        }
        
        wrapper.orderByDesc(StoreProduct::getId);
        return Result.success(storeProductService.page(pageParam, wrapper));
    }
    
    /**
     * 提取公共过滤条件构造器 (公用搜索条件：关键词、分类、价格范围、销量范围等)
     * 这样在计算各个状态数量以及获取列表时，都会自动叠加这些搜索限制，保证查询与统计的一致性。
     *
     * @param storeName   商品搜索关键词
     * @param fieldKey    搜索类型
     * @param cateId      分类ID
     * @param productType 商品类型
     * @param priceRange  价格区间
     * @param salesRange  销量区间
     * @param stockRange  库存区间
     * @param supplierId  供应商ID
     * @return LambdaQueryWrapper<StoreProduct> 查询包装器
     */
    private LambdaQueryWrapper<StoreProduct> buildBaseQuery(String storeName, String fieldKey, Integer cateId, Integer productType, String priceRange, String salesRange, String stockRange, Integer supplierId) {
        LambdaQueryWrapper<StoreProduct> w = new LambdaQueryWrapper<>();
        
        // PHP 侧默认会附加 mer_id=0，用于筛选平台商品；这里保持一致
        w.eq(StoreProduct::getMerId, 0);

        // PHP 侧：传 supplier_id 时查询供应商商品，否则默认 pid=0（只查主商品）
        if (supplierId != null && supplierId > 0) {
            w.eq(StoreProduct::getRelationId, supplierId);
            w.eq(StoreProduct::getType, 2);
        } else {
            w.eq(StoreProduct::getPid, 0);
        }

        if (storeName != null && !storeName.isEmpty()) {
            if ("store_name".equals(fieldKey)) {
                w.like(StoreProduct::getStoreName, storeName);
            } else if ("product_id".equals(fieldKey)) {
                w.eq(StoreProduct::getId, storeName);
            } else {
                w.and(q -> q.like(StoreProduct::getStoreName, storeName).or().like(StoreProduct::getKeyword, storeName).or().eq(StoreProduct::getId, storeName));
            }
        }
        
        if (cateId != null) w.eq(StoreProduct::getCateId, String.valueOf(cateId));
        if (productType != null) w.eq(StoreProduct::getProductType, productType);

        // 处理 range，格式如 "10-100" 或 "10-" 或 "-100"
        applyRange(w, StoreProduct::getPrice, priceRange, true);
        applyRange(w, StoreProduct::getSales, salesRange, false);
        applyRange(w, StoreProduct::getStock, stockRange, false);

        return w;
    }

    /**
     * 辅助方法：解析区间字符串并应用范围过滤条件
     * 
     * @param w         查询包装器
     * @param column    对应的字段属性函数
     * @param rangeStr  前端传来的区间字符串（例： "10-100", "10-", "-100"）
     * @param isDecimal 是否为小数类型字段
     */
    private <T> void applyRange(LambdaQueryWrapper<StoreProduct> w, com.baomidou.mybatisplus.core.toolkit.support.SFunction<StoreProduct, ?> column, String rangeStr, boolean isDecimal) {
        if (rangeStr != null && rangeStr.contains("-")) {
            String[] parts = rangeStr.split("-", 2);
            String minStr = parts[0];
            String maxStr = parts.length > 1 ? parts[1] : "";
            if (!minStr.isEmpty()) {
                if (isDecimal) w.ge(column, new java.math.BigDecimal(minStr));
                else w.ge(column, Integer.parseInt(minStr));
            }
            if (!maxStr.isEmpty()) {
                if (isDecimal) w.le(column, new java.math.BigDecimal(maxStr));
                else w.le(column, Integer.parseInt(maxStr));
            }
        }
    }

    /**
     * 获取商品各分类(Tab)状态数量统计
     * 针对所有 `type` 的查询，提取公共搜索条件以保证筛选时所有页签数值准确联动。
     * 且附带一分钟缓存控制，减轻多次复杂查询造成的数据库压力。
     *
     * @param storeName   商品搜索关键词（对应 PHP 的 store_name，结合 field_key 使用）
     * @param fieldKey    搜索类型：store_name(商品名称), product_id(商品ID), all(全部)
     * @param cateId      商品分类ID
     * @param productType 商品类型
     * @param priceRange  售价区间，格式如 "10-100" 或 "10-" 或 "-100"
     * @param salesRange  销量区间，格式如 "10-100"
     * @param stockRange  库存区间，格式如 "10-100"
     * @param supplierId  供应商ID
     * @return 包含各分类对应数量的 Map (如：selling: 10, warehouse: 5...)
     */
    @GetMapping("/status_statistics")
    public Result<Map<String, Object>> statusStatistics(@RequestParam(required = false, name = "store_name") String storeName,
                                                        @RequestParam(required = false, name = "field_key") String fieldKey,
                                                        @RequestParam(required = false, name = "cate_id") Integer cateId,
                                                        @RequestParam(required = false, name = "product_type") Integer productType,
                                                        @RequestParam(required = false, name = "price_range") String priceRange,
                                                        @RequestParam(required = false, name = "sales_range") String salesRange,
                                                        @RequestParam(required = false, name = "stock_range") String stockRange,
                                                        @RequestParam(required = false, name = "supplier_id") Integer supplierId) {
        
        // 缓存键：组合所有查询参数
        String cacheKey = String.format("%s_%s_%s_%s_%s_%s_%s_%s", storeName, fieldKey, cateId, productType, priceRange, salesRange, stockRange, supplierId);
        CacheData cache = statsCache.get(cacheKey);
        
        // 1分钟内查询使用缓存
        if (cache != null && System.currentTimeMillis() < cache.expireTime) {
            return Result.success(cache.data);
        }

        Map<String, Object> stats = new HashMap<>();

        // 提取公共过滤条件构造器 (公用搜索条件：关键词、分类、价格范围、销量范围等)
        // 这样在计算各个状态数量时，都会自动叠加这些搜索限制
        java.util.function.Supplier<LambdaQueryWrapper<StoreProduct>> baseWrapper = () -> buildBaseQuery(storeName, fieldKey, cateId, productType, priceRange, salesRange, stockRange, supplierId);
        
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
