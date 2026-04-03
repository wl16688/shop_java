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
@RequestMapping("/api/admin/extend/product_product_list")
public class MockProductListController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String type) {
        List<Map<String, Object>> list = new ArrayList<>();
        Random random = new Random();
        String[] names = {"Apple iPhone 15 Pro", "华为 Mate 60 Pro", "小米 14 Ultra", "大疆 DJI Mini 4 Pro", "索尼 PlayStation 5"};
        String[] images = {
            "https://img.alicdn.com/tfs/TB1X7oXzXzqK1RjSZFjXXcbOXXa-512-512.png",
            "https://img.alicdn.com/tfs/TB1V2eOrKSSMeJjSZFsXXcXhpXa-130-130.png"
        };

        for (int i = 0; i < 10; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("store_name", names[random.nextInt(names.length)] + " 官方正品");
            item.put("image", images[random.nextInt(images.length)]);
            item.put("price", String.format("%.2f", 3000 + random.nextDouble() * 5000));
            item.put("sales", random.nextInt(10000));
            item.put("stock", random.nextInt(500));
            item.put("sort", 100 - i);
            item.put("is_show", "1".equals(type) ? 1 : ("2".equals(type) ? 0 : random.nextInt(2)));
            item.put("add_time", "2024-03-01 10:00:00");
            
            if (keyword != null && !keyword.isEmpty() && !item.get("store_name").toString().contains(keyword)) {
                continue;
            }
            list.add(item);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("records", list);
        data.put("total", list.size());
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }
}
