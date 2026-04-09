package com.shop.shop_java.controller.p3;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/setting_system_admin_index")
public class AdminController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer status) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] accounts = {"admin", "editor", "finance_01"};
        String[] realNames = {"超级管理员", "内容编辑", "财务出纳"};
        String[][] roles = {{"超级管理员"}, {"运营人员", "内容审核"}, {"财务管理"}};
        for(int i=0; i<3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("account", accounts[i]);
            map.put("real_name", realNames[i]);
            map.put("roles", roles[i]);
            map.put("status", 1);
            map.put("last_ip", "192.168.1." + (100 + i));
            map.put("last_time", "2024-05-18 10:20:30");
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
