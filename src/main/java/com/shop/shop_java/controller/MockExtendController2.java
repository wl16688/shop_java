package com.shop.shop_java.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/admin/extend")
public class MockExtendController2 {

    // 内存数据库，每个模块维护自己的列表数据
    private static final Map<String, List<Map<String, Object>>> DB = new ConcurrentHashMap<>();
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(100);

    // 初始化一些默认数据，特别是对于商品列表
    private List<Map<String, Object>> getModuleData(String module) {
        return DB.computeIfAbsent(module, k -> {
            List<Map<String, Object>> list = new ArrayList<>();
            if ("product_product_list".equals(k)) {
                // 模拟不同状态的商品
                // 状态定义：1:出售中, 2:仓库中, 4:已售罄, 5:库存预警, 6:回收站, 0:待审核, -1:审核未通过, -2:强制下架
                list.add(createProductItem(1, "Apple iPhone 15 Pro", 7999.00, 345, 100, 1));
                list.add(createProductItem(2, "华为 Mate 60 Pro", 6999.00, 298, 50, 1));
                list.add(createProductItem(3, "小米 14 Ultra", 5999.00, 256, 200, 1));
                list.add(createProductItem(4, "DJI 大疆无人机", 4999.00, 189, 0, 4)); // 已售罄
                list.add(createProductItem(5, "Sony 降噪耳机", 1999.00, 150, 5, 5));  // 库存预警
                list.add(createProductItem(6, "测试仓库商品A", 99.00, 0, 100, 2));   // 仓库中
                list.add(createProductItem(7, "测试回收站商品", 9.90, 0, 0, 6));    // 回收站
                list.add(createProductItem(8, "待审核测试商品", 299.00, 0, 50, 0)); // 待审核
                list.add(createProductItem(9, "违规下架商品", 999.00, 10, 20, -2)); // 强制下架
            } else {
                Map<String, Object> item = new HashMap<>();
                item.put("id", 1);
                item.put("name", "测试_" + k);
                item.put("status", 1);
                item.put("createTime", "2023-10-24 10:00:00");
                item.put("remark", "初始默认数据");
                list.add(item);
            }
            return list;
        });
    }

    private Map<String, Object> createProductItem(int id, String name, double price, int sales, int stock, int status) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", id);
        item.put("name", name);
        item.put("price", price);
        item.put("sales", sales);
        item.put("stock", stock);
        item.put("status", status); // 关键状态字段
        item.put("createTime", "2024-05-18 12:00:00");
        item.put("remark", "商品模拟数据");
        return item;
    }

    // 1. 提供头部状态数量统计接口 (重点支持 type_header)
    @GetMapping("/{module}/type_header")
    public Map<String, Object> getTypeHeader(@PathVariable String module) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        
        List<Map<String, Object>> data = getModuleData(module);
        
        // 统计各类状态数量
        int onsale = 0, forsale = 0, outofstock = 0, policeforce = 0, delete = 0, unVerify = 0, refuseVerify = 0, removeVerify = 0;
        
        for (Map<String, Object> item : data) {
            Object statusObj = item.get("status");
            if (statusObj instanceof Integer) {
                int status = (Integer) statusObj;
                if (status == 1) onsale++;
                else if (status == 2) forsale++; // 原网站约定: 2是仓库中
                else if (status == 4) outofstock++;
                else if (status == 5) policeforce++;
                else if (status == 6) delete++;
                else if (status == 0) unVerify++;
                else if (status == -1) refuseVerify++;
                else if (status == -2) removeVerify++;
            }
        }
        
        List<Map<String, Object>> headerList = new ArrayList<>();
        headerList.add(createHeaderItem(1, "出售中", onsale));
        headerList.add(createHeaderItem(2, "仓库中", forsale));
        headerList.add(createHeaderItem(4, "已售罄", outofstock));
        headerList.add(createHeaderItem(5, "库存预警", policeforce));
        headerList.add(createHeaderItem(6, "回收站", delete));
        headerList.add(createHeaderItem(0, "待审核", unVerify));
        headerList.add(createHeaderItem(-1, "审核未通过", refuseVerify));
        headerList.add(createHeaderItem(-2, "强制下架", removeVerify));
        
        result.put("data", headerList);
        return result;
    }

    private Map<String, Object> createHeaderItem(int type, String name, int count) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("name", name);
        map.put("count", count);
        return map;
    }

    // 2. 列表接口支持类型过滤
    @GetMapping("/{module}/list")
    public Map<String, Object> getList(@PathVariable String module, 
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer type) {
        List<Map<String, Object>> allData = getModuleData(module);
        List<Map<String, Object>> filteredData = new ArrayList<>();

        for (Map<String, Object> item : allData) {
            boolean match = true;
            // 过滤类型
            if (type != null) {
                Object statusObj = item.get("status");
                if (statusObj instanceof Integer) {
                    if (!statusObj.equals(type)) {
                        match = false;
                    }
                }
            }
            // 过滤关键字
            if (keyword != null && !keyword.isEmpty() && match) {
                String name = (String) item.get("name");
                if (name == null || !name.contains(keyword)) {
                    match = false;
                }
            }
            if (match) {
                filteredData.add(item);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", filteredData);
        result.put("total", filteredData.size());
        return result;
    }

    @GetMapping("/{module}/detail")
    public Map<String, Object> getDetail(@PathVariable String module, @RequestParam Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        
        List<Map<String, Object>> data = getModuleData(module);
        for (Map<String, Object> item : data) {
            if (item.get("id").equals(id)) {
                result.put("data", item);
                return result;
            }
        }
        result.put("code", 404);
        result.put("msg", "未找到数据");
        return result;
    }

    @PostMapping("/{module}/save")
    public Map<String, Object> saveData(@PathVariable String module, @RequestBody Map<String, Object> params) {
        List<Map<String, Object>> data = getModuleData(module);
        Object idObj = params.get("id");
        
        if (idObj == null) {
            // 新增
            params.put("id", ID_GENERATOR.incrementAndGet());
            params.put("createTime", "2024-05-18 12:00:00");
            data.add(0, params);
        } else {
            // 更新
            int id = idObj instanceof Integer ? (Integer) idObj : Integer.parseInt(idObj.toString());
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).get("id").equals(id)) {
                    // 合并属性
                    Map<String, Object> existItem = data.get(i);
                    existItem.putAll(params);
                    break;
                }
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "保存成功");
        return result;
    }

    // 批量状态修改接口 (上架、下架、移入回收站等)
    @PostMapping("/{module}/batch_status")
    public Map<String, Object> batchStatus(@PathVariable String module, @RequestBody Map<String, Object> params) {
        List<Map<String, Object>> data = getModuleData(module);
        List<Integer> ids = (List<Integer>) params.get("ids");
        Integer targetStatus = (Integer) params.get("status");
        
        // 用于跨页全选过滤
        Object typeObj = params.get("type");
        Integer filterType = typeObj != null ? Integer.parseInt(typeObj.toString()) : null;

        if (targetStatus != null) {
            for (Map<String, Object> item : data) {
                // 如果传入了 ids，按 ids 操作
                if (ids != null && !ids.isEmpty()) {
                    if (ids.contains(item.get("id"))) {
                        item.put("status", targetStatus);
                    }
                } 
                // 否则是跨页全选操作，通过原来的 type 筛选出需要被操作的商品
                else if (filterType != null) {
                    Object statusObj = item.get("status");
                    if (statusObj instanceof Integer && statusObj.equals(filterType)) {
                        item.put("status", targetStatus);
                    }
                }
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "批量操作成功");
        return result;
    }

    @PostMapping("/{module}/delete")
    public Map<String, Object> deleteData(@PathVariable String module, @RequestBody Map<String, Object> params) {
        List<Map<String, Object>> data = getModuleData(module);
        
        Object idObj = params.get("id");
        List<Integer> ids = (List<Integer>) params.get("ids");
        
        if (idObj != null) {
            int id = idObj instanceof Integer ? (Integer) idObj : Integer.parseInt(idObj.toString());
            data.removeIf(item -> item.get("id").equals(id));
        } else if (ids != null) {
            data.removeIf(item -> ids.contains(item.get("id")));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "删除成功");
        return result;
    }

    @GetMapping(value = "/{module}/export", produces = "text/csv;charset=utf-8")
    public String exportData(@PathVariable String module) {
        return "ID,名称,状态,创建时间,备注\n1,测试_" + module + ",1,2023-10-24 10:00:00,导出测试数据\n";
    }
}
