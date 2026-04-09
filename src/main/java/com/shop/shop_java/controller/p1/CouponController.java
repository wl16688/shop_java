package com.shop.shop_java.controller.p1;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/marketing_store_coupon_issue_create")
public class CouponController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i=0; i<4; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("coupon_title", "满" + (100 + i * 100) + "减" + (10 + i * 10) + "通用券");
            map.put("coupon_price", 10 + i * 10);
            map.put("use_min_price", 100 + i * 100);
            map.put("total_count", 1000);
            map.put("remain_count", 500 + i * 100);
            map.put("receive_type", i % 2 == 0 ? 1 : 2);
            map.put("status", 1);
            map.put("end_time", "2024-12-31 23:59:59");
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
