package com.shop.shop_java.controller.p2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/supplier_orderlist_index")
public class SupplierOrderController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer status) {
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i=0; i<5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("order_id", "SUP" + (60000000000L + i * 888));
            map.put("supplier_name", i % 2 == 0 ? "杭州某某食品专营店" : "深圳数码科技有限公司");
            map.put("real_name", "买家_" + i);
            map.put("pay_price", String.format("%.2f", 99.0 + i * 30));
            map.put("status", i % 3); // 0待发货 1待收货 2已完成
            map.put("add_time", "2024-05-12 16:20:00");
            list.add(map);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("records", list);
        data.put("total", list.size());
        
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "success");
        res.put("data", data);
        return res;
    }
}
