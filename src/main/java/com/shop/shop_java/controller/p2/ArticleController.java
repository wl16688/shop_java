package com.shop.shop_java.controller.p2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/extend/content_article_index")
public class ArticleController {

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] titles = {"2024夏季新品发布会圆满成功", "如何挑选适合自己的护肤品？", "双十一大促玩法揭秘"};
        for(int i=0; i<3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("image_input", "https://img.alicdn.com/tfs/TB1V2eOrKSSMeJjSZFsXXcXhpXa-130-130.png");
            map.put("title", titles[i]);
            map.put("author", "官方小编");
            map.put("visit", 1000 + i * 500);
            map.put("is_show", 1);
            map.put("create_time", "2024-05-15 09:00:00");
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
