package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.shop_java.entity.SystemConfig;
import com.shop.shop_java.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/system/config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService configService;

    @GetMapping("/reply_config")
    public Map<String, Object> getReplyConfig() {
        List<String> keys = Arrays.asList(
                "reply_point_switch", "reply_point_once", "reply_point_day",
                "reply_exp_switch", "reply_exp_once", "reply_exp_day"
        );
        LambdaQueryWrapper<SystemConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SystemConfig::getMenuName, keys);
        List<SystemConfig> list = configService.list(wrapper);

        Map<String, String> configMap = new HashMap<>();
        for (SystemConfig config : list) {
            String val = config.getValue();
            if (val != null && val.startsWith("\"") && val.endsWith("\"")) {
                val = val.substring(1, val.length() - 1);
            }
            configMap.put(config.getMenuName(), val);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", configMap);
        return result;
    }

    @PostMapping("/reply_config/save")
    public Map<String, Object> saveReplyConfig(@RequestBody Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = "\"" + entry.getValue() + "\"";
            
            LambdaQueryWrapper<SystemConfig> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SystemConfig::getMenuName, key);
            SystemConfig config = configService.getOne(wrapper);
            if (config != null) {
                config.setValue(value);
                configService.updateById(config);
            } else {
                config = new SystemConfig();
                config.setMenuName(key);
                config.setValue(value);
                configService.save(config);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "保存成功");
        return result;
    }
}
