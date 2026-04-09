package com.shop.shop_java.controller.p2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/finance_user_extract_index")
public class UserExtractController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer status) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] types = {"微信零钱", "支付宝", "银行卡"};
        for(int i=0; i<6; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("nickname", "分销员_" + (2000 + i));
            map.put("extract_price", String.format("%.2f", 50.0 + i * 100));
            map.put("extract_type", types[i % 3]);
            map.put("account", "账号：" + (13800000000L + i * 1111));
            map.put("status", i % 3 == 0 ? 0 : (i % 3 == 1 ? 1 : -1));
            map.put("create_time", "2024-05-10 14:00:00");
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
