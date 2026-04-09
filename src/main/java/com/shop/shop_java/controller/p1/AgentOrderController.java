package com.shop.shop_java.controller.p1;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/agent_order")
public class AgentOrderController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i=0; i<5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("order_id", "AG" + (80000000000L + i * 1234));
            map.put("buyer_name", "买家用户_" + i);
            map.put("agent_name", "推广员_王五");
            map.put("pay_price", String.format("%.2f", 200.0 + i * 50));
            map.put("brokerage", String.format("%.2f", 20.0 + i * 5));
            map.put("status", i % 3 == 0 ? 0 : (i % 3 == 1 ? 1 : -1));
            map.put("create_time", "2024-04-05 10:00:00");
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
