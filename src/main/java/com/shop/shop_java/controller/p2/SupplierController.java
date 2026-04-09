package com.shop.shop_java.controller.p2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/supplier_supplier_index")
public class SupplierController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] names = {"杭州某某食品专营店", "广州服装批发商", "深圳数码科技有限公司"};
        for(int i=0; i<3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("supplier_name", names[i]);
            map.put("contact_name", "联系人_" + i);
            map.put("phone", "1360000" + (1000 + i));
            map.put("product_count", 50 + i * 10);
            map.put("order_count", 200 + i * 50);
            map.put("status", 1);
            map.put("create_time", "2023-12-01 10:00:00");
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
