package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.entity.StoreProductEnsure;
import com.shop.shop_java.service.StoreProductEnsureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/store/product/ensure")
public class StoreProductEnsureController {

    @Autowired
    private StoreProductEnsureService ensureService;

    @GetMapping("/list")
    public Map<String, Object> getList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) String keyword) {

        LambdaQueryWrapper<StoreProductEnsure> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(StoreProductEnsure::getName, keyword);
        }
        wrapper.orderByDesc(StoreProductEnsure::getSort).orderByDesc(StoreProductEnsure::getId);

        Page<StoreProductEnsure> pageParam = new Page<>(page, limit);
        Page<StoreProductEnsure> resultPage = ensureService.page(pageParam, wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", resultPage);
        return result;
    }

    @PostMapping("/list")
    public Map<String, Object> postList(@RequestBody Map<String, Object> params) {
        Integer page = params.get("page") != null ? Integer.parseInt(params.get("page").toString()) : 1;
        Integer limit = params.get("limit") != null ? Integer.parseInt(params.get("limit").toString()) : 10;
        String keyword = params.get("keyword") != null ? params.get("keyword").toString() : null;
        return getList(page, limit, keyword);
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody StoreProductEnsure ensure) {
        if (ensure.getId() == null) {
            ensure.setAddTime((int) (System.currentTimeMillis() / 1000));
            ensureService.save(ensure);
        } else {
            ensureService.updateById(ensure);
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
            ensureService.removeById(id);
        }
        Object idsObj = params.get("ids");
        if (idsObj instanceof List) {
            List<?> idsList = (List<?>) idsObj;
            List<Integer> ids = idsList.stream()
                    .map(o -> o instanceof Integer ? (Integer) o : Integer.parseInt(o.toString()))
                    .collect(Collectors.toList());
            ensureService.removeByIds(ids);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "删除成功");
        return result;
    }
}
