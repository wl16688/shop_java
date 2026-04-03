package com.shop.shop_java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/admin/order")
public class MockOrderListController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String status) {
        List<Map<String, Object>> list = new ArrayList<>();
        Random random = new Random();
        String[] names = {"张三", "李四", "王五", "赵六", "陈七", "刘八"};
        
        for (int i = 0; i < 15; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("order_id", "wx" + (10000000000L + random.nextInt(900000000)));
            item.put("order_type", random.nextInt(2) + 1); // 1 or 2
            item.put("real_name", names[random.nextInt(names.length)]);
            item.put("pay_price", String.format("%.2f", 50 + random.nextDouble() * 500));
            item.put("paid", random.nextInt(2)); // 0 or 1
            item.put("status", random.nextInt(4)); // 0, 1, 2, 3
            item.put("add_time", "2024-03-" + String.format("%02d", random.nextInt(28) + 1) + " 10:20:30");
            
            // Filter
            if (keyword != null && !keyword.isEmpty() && !item.get("order_id").toString().contains(keyword)) {
                continue;
            }
            if (status != null && !status.isEmpty() && !status.equals(item.get("status").toString())) {
                continue;
            }
            list.add(item);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }
}
