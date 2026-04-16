package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.entity.StoreProductLabel;
import com.shop.shop_java.service.StoreProductLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/store/product_label")
public class StoreProductLabelController {

    @Autowired
    private StoreProductLabelService productLabelService;

    @GetMapping("/list")
    public Map<String, Object> getList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer labelCate) {

        LambdaQueryWrapper<StoreProductLabel> wrapper = new LambdaQueryWrapper<>();
        if (labelCate != null && labelCate > 0) {
            wrapper.eq(StoreProductLabel::getLabelCate, labelCate);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(StoreProductLabel::getLabelName, keyword);
        }
        wrapper.orderByDesc(StoreProductLabel::getSort).orderByDesc(StoreProductLabel::getId);

        Page<StoreProductLabel> pageParam = new Page<>(page, limit);
        Page<StoreProductLabel> resultPage = productLabelService.page(pageParam, wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", resultPage);
        return result;
    }

    @GetMapping("/detail")
    public Map<String, Object> getDetail(@RequestParam Integer id) {
        StoreProductLabel label = productLabelService.getById(id);
        Map<String, Object> result = new HashMap<>();
        if (label != null) {
            result.put("code", 200);
            result.put("msg", "success");
            result.put("data", label);
        } else {
            result.put("code", 404);
            result.put("msg", "数据不存在");
        }
        return result;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody StoreProductLabel label) {
        if (label.getId() == null) {
            label.setAddTime((int) (System.currentTimeMillis() / 1000));
            productLabelService.save(label);
        } else {
            productLabelService.updateById(label);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "保存成功");
        return result;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            Object idObj = params.get("id");
            if (idObj != null) {
                Integer id = idObj instanceof Integer ? (Integer) idObj : Integer.parseInt(idObj.toString());
                productLabelService.removeById(id);
            }
            
            Object idsObj = params.get("ids");
            if (idsObj instanceof List) {
                List<?> idsList = (List<?>) idsObj;
                List<Integer> ids = idsList.stream()
                        .map(o -> o instanceof Integer ? (Integer) o : Integer.parseInt(o.toString()))
                        .collect(Collectors.toList());
                productLabelService.removeByIds(ids);
            }
            result.put("code", 200);
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "删除失败: " + e.getMessage());
        }
        return result;
    }
}
