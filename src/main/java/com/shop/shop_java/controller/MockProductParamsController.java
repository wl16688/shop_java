package com.shop.shop_java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/product")
public class MockProductParamsController {

    @GetMapping("/product/brand")
    public Map<String, Object> getBrandList(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int limit,
                                            @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] brands = {"苹果", "华为", "小米", "三星", "OPPO", "vivo", "索尼", "大疆"};
        
        for (int i = 0; i < brands.length; i++) {
            if (keyword != null && !keyword.isEmpty() && !brands[i].contains(keyword)) {
                continue;
            }
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("brand_name", brands[i]);
            item.put("brand_icon", "https://img.alicdn.com/tfs/TB1X7oXzXzqK1RjSZFjXXcbOXXa-512-512.png");
            item.put("sort", 100 - i);
            item.put("is_show", 1);
            item.put("create_time", "2024-02-01 12:00:00");
            list.add(item);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        return success(data);
    }

    @GetMapping("/unitList")
    public Map<String, Object> getUnitList(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int limit,
                                           @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] units = {"件", "个", "套", "双", "台", "瓶", "盒", "千克", "克", "斤"};
        
        for (int i = 0; i < units.length; i++) {
            if (keyword != null && !keyword.isEmpty() && !units[i].contains(keyword)) {
                continue;
            }
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("unit_name", units[i]);
            item.put("sort", 50 - i);
            item.put("create_time", "2024-02-02 09:30:00");
            list.add(item);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        return success(data);
    }

    @GetMapping("/specs")
    public Map<String, Object> getSpecsList(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int limit,
                                            @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] templates = {"手机参数模板", "电脑参数模板", "服装参数模板", "鞋子参数模板"};
        List<List<String>> specsList = Arrays.asList(
            Arrays.asList("颜色", "内存", "存储", "网络制式"),
            Arrays.asList("处理器", "内存", "硬盘", "显卡", "屏幕尺寸"),
            Arrays.asList("尺码", "颜色", "材质", "风格"),
            Arrays.asList("鞋码", "颜色", "款式", "适用季节")
        );
        
        for (int i = 0; i < templates.length; i++) {
            if (keyword != null && !keyword.isEmpty() && !templates[i].contains(keyword)) {
                continue;
            }
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("template_name", templates[i]);
            item.put("specs", specsList.get(i));
            item.put("sort", 10 - i);
            item.put("create_time", "2024-02-03 14:15:00");
            list.add(item);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        return success(data);
    }

    @GetMapping("/ensure")
    public Map<String, Object> getEnsureList(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int limit,
                                             @RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] names = {"7天无理由退货", "正品保证", "极速退款", "免费上门取退"};
        String[] descs = {
            "满足相应条件时，消费者可申请“7天无理由退货”",
            "该商品由品牌方或授权代理商直接供货，100%正品",
            "满足退款条件时，退款将在1-3个工作日内原路退回",
            "退换货时，支持快递员免费上门取件"
        };
        
        for (int i = 0; i < names.length; i++) {
            if (keyword != null && !keyword.isEmpty() && !names[i].contains(keyword)) {
                continue;
            }
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("name", names[i]);
            item.put("desc", descs[i]);
            item.put("icon", "https://img.alicdn.com/tfs/TB1V2eOrKSSMeJjSZFsXXcXhpXa-130-130.png");
            item.put("sort", 20 - i);
            item.put("is_show", 1);
            item.put("create_time", "2024-02-04 16:45:00");
            list.add(item);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        return success(data);
    }

    private Map<String, Object> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }
}
