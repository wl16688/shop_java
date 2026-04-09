package com.shop.shop_java.controller.p1;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/marketing_store_seckill_list")
public class SeckillController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer status) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] titles = {"【限时秒杀】戴森 Action 4 无人机", "【整点秒】SK-II 神仙水230ml"};
        for(int i=0; i<2; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("image", "https://img.alicdn.com/tfs/TB1X7oXzXzqK1RjSZFjXXcbOXXa-512-512.png");
            map.put("title", titles[i]);
            map.put("price", String.format("%.2f", 1000.0 + i * 500));
            map.put("seckill_price", String.format("%.2f", 500.0 + i * 200));
            map.put("quota", 50);
            map.put("sales", 45 - i * 10);
            map.put("status", 1);
            map.put("is_show", 1);
            map.put("stop_time", "2024-04-15 12:00:00");
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
