package com.shop.shop_java.controller.p3;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/system_maintain_system_log_index")
public class SystemLogController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String admin_name,
                                       @RequestParam(required = false) String path) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] pages = {"登录页面", "商品列表", "订单详情", "用户管理"};
        String[] paths = {"/api/admin/login", "/api/admin/product/list", "/api/admin/order/detail", "/api/admin/user/list"};
        String[] methods = {"POST", "GET", "GET", "GET"};
        for(int i=0; i<10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("admin_name", i % 2 == 0 ? "admin" : "editor");
            map.put("path", paths[i % 4]);
            map.put("page", pages[i % 4]);
            map.put("method", methods[i % 4]);
            map.put("ip", "127.0.0.1");
            map.put("add_time", "2024-05-20 14:0" + i + ":00");
            list.add(map);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("records", list);
        data.put("total", 500);
        
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "success");
        res.put("data", data);
        return res;
    }
}
