package com.shop.shop_java.controller.p1;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/marketing_store_combination_index")
public class CombinationController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer status) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] titles = {"【2人拼】新鲜特级红富士苹果", "【3人拼】全棉加厚春秋四件套", "【5人拼】便携式智能榨汁机"};
        for(int i=0; i<3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("image", "https://img.alicdn.com/tfs/TB1V2eOrKSSMeJjSZFsXXcXhpXa-130-130.png");
            map.put("title", titles[i]);
            map.put("price", String.format("%.2f", 50.0 + i * 20));
            map.put("pink_price", String.format("%.2f", 30.0 + i * 15));
            map.put("people", i + 2);
            map.put("count_people_all", 100 + i * 50);
            map.put("status", i % 2 == 0 ? 1 : 0);
            map.put("is_show", 1);
            map.put("stop_time", "2024-05-01 23:59:59");
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
