package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.shop_java.entity.Category;
import com.shop.shop_java.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/store/product_label_cate")
public class StoreProductLabelCateController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword) {
        // 商品标签分组对应的 group 为 2
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getGroup, 2);
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Category::getName, keyword);
        }
        wrapper.orderByDesc(Category::getSort).orderByDesc(Category::getId);

        List<Category> list = categoryService.list(wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", list);
        return result;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody Category category) {
        category.setGroup(2); // 强制设置为商品标签分组
        if (category.getId() == null) {
            category.setAddTime((int) (System.currentTimeMillis() / 1000));
            categoryService.save(category);
        } else {
            categoryService.updateById(category);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "保存成功");
        return result;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> params) {
        Object idObj = params.get("id");
        if (idObj != null) {
            Integer id = idObj instanceof Integer ? (Integer) idObj : Integer.parseInt(idObj.toString());
            categoryService.removeById(id);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "删除成功");
        return result;
    }
}
