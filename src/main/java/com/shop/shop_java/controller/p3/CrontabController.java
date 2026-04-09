package com.shop.shop_java.controller.p3;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/system_crontab_create")
public class CrontabController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] names = {"自动取消未支付订单", "拼团自动成团", "定时清理系统日志"};
        String[] cycles = {"每5分钟执行一次", "每小时执行一次", "每天凌晨2点执行"};
        for(int i=0; i<3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("name", names[i]);
            map.put("mark", "自动处理过期的数据，保证系统性能");
            map.put("type", i % 2 == 0 ? 1 : 2);
            map.put("cycle", cycles[i]);
            map.put("is_open", 1);
            map.put("last_execution_time", "2024-05-19 09:00:00");
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
