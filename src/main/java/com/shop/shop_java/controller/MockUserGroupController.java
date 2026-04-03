package com.shop.shop_java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/user/group")
public class MockUserGroupController {

    @GetMapping
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {
        
        List<Map<String, Object>> list = new ArrayList<>();
        
        String[] names = {"未分组", "新用户", "老用户", "活跃用户", "流失用户", "VIP分组", "测试分组"};
        int[] counts = {1502, 340, 890, 450, 120, 50, 5};
        
        for (int i = 0; i < names.length; i++) {
            if (keyword != null && !keyword.isEmpty() && !names[i].contains(keyword)) {
                continue;
            }
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("group_name", names[i]);
            item.put("count", counts[i]);
            item.put("create_time", "2024-01-" + String.format("%02d", (i % 30) + 1) + " 10:00:00");
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
