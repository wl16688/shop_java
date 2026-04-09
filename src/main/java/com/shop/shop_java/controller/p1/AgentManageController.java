package com.shop.shop_java.controller.p1;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/agent_agent_manage_index")
public class AgentManageController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] names = {"李四", "王五", "赵六", "周八"};
        for(int i=0; i<names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("uid", 1000 + i);
            map.put("avatar", "https://img.alicdn.com/tfs/TB1V2eOrKSSMeJjSZFsXXcXhpXa-130-130.png");
            map.put("nickname", names[i]);
            map.put("phone", "1390000111" + i);
            map.put("level_name", i % 2 == 0 ? "高级推客" : "初级推客");
            map.put("spread_count", 5 + i * 10);
            map.put("order_count", 10 + i * 20);
            map.put("brokerage_price", String.format("%.2f", 100.0 + i * 150));
            map.put("status", 1);
            map.put("create_time", "2023-10-01 08:00:00");
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
