package com.shop.shop_java.controller.p2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/statistic_capital")
public class StatisticCapitalController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer pm) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] titles = {"用户充值", "购买商品", "佣金入账", "提现扣除"};
        for(int i=0; i<8; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("order_id", "流水号_" + (5000000 + i));
            map.put("nickname", "用户_" + (100 + i));
            map.put("pm", i % 2 == 0 ? 1 : 0); // 1收入 0支出
            map.put("number", String.format("%.2f", 10.0 + i * 20));
            map.put("title", titles[i % 4]);
            map.put("mark", "资金流水变动备注信息");
            map.put("create_time", "2024-05-11 09:30:00");
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
