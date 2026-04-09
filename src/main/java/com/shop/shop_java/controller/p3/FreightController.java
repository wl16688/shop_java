package com.shop.shop_java.controller.p3;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/setting_freight_shipping_templates_list")
public class FreightController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] names = {"全国包邮模板", "偏远地区不包邮", "按重量计费模板"};
        for(int i=0; i<3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("name", names[i]);
            map.put("type", i % 3 + 1); // 1按件数 2按重量 3按体积
            map.put("appoint", i == 0 ? 1 : 0);
            map.put("sort", 100 - i);
            map.put("create_time", "2023-08-10 12:00:00");
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
