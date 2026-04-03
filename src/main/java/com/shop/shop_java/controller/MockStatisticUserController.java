package com.shop.shop_java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/admin/statistic/user")
public class MockStatisticUserController {

    private Random random = new Random();

    @GetMapping("/get_basic")
    public Map<String, Object> getBasic() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalUser", 15234);
        data.put("todayNew", 128);
        data.put("yesterdayNew", 145);
        data.put("monthNew", 3450);
        data.put("totalVip", 2341);
        data.put("totalVisitor", 45120);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }

    @GetMapping("/get_trend")
    public Map<String, Object> getTrend() {
        List<String> dates = new ArrayList<>();
        List<Integer> newUsers = new ArrayList<>();
        List<Integer> activeUsers = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            dates.add("2024-04-" + String.format("%02d", i));
            newUsers.add(50 + random.nextInt(150));
            activeUsers.add(500 + random.nextInt(300));
        }

        Map<String, Object> data = new HashMap<>();
        data.put("dates", dates);
        data.put("newUsers", newUsers);
        data.put("activeUsers", activeUsers);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }

    @GetMapping("/get_region")
    public Map<String, Object> getRegion() {
        List<Map<String, Object>> data = new ArrayList<>();
        String[] regions = {"广东省", "浙江省", "江苏省", "北京市", "上海市", "山东省", "河南省", "四川省", "福建省", "湖南省"};
        int total = 15234;
        
        for (int i = 0; i < regions.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", regions[i]);
            item.put("value", 1000 - i * 80 + random.nextInt(50));
            item.put("percent", String.format("%.2f", (double)(int)item.get("value") / total * 100) + "%");
            data.add(item);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }

    @GetMapping("/get_sex")
    public Map<String, Object> getSex() {
        List<Map<String, Object>> data = new ArrayList<>();
        
        Map<String, Object> male = new HashMap<>();
        male.put("name", "男");
        male.put("value", 6800);
        
        Map<String, Object> female = new HashMap<>();
        female.put("name", "女");
        female.put("value", 7200);
        
        Map<String, Object> unknown = new HashMap<>();
        unknown.put("name", "未知");
        unknown.put("value", 1234);

        data.add(male);
        data.add(female);
        data.add(unknown);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }

    @GetMapping("/get_excel")
    public Map<String, Object> getExcel() {
        // Just return success for export mock
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "导出成功");
        return response;
    }
}
