package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.entity.SystemForm;
import com.shop.shop_java.service.SystemFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/system/form")
public class SystemFormController {

    @Autowired
    private SystemFormService systemFormService;

    @GetMapping("/index")
    public Map<String, Object> getList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) String keyword) {

        LambdaQueryWrapper<SystemForm> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(SystemForm::getName, keyword);
        }
        wrapper.orderByDesc(SystemForm::getId);

        Page<SystemForm> pageParam = new Page<>(page, limit);
        Page<SystemForm> resultPage = systemFormService.page(pageParam, wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", resultPage);
        return result;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody SystemForm form) {
        if (form.getId() == null) {
            form.setAddTime((int) (System.currentTimeMillis() / 1000));
            systemFormService.save(form);
        } else {
            form.setUpdateTime((int) (System.currentTimeMillis() / 1000));
            systemFormService.updateById(form);
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
            systemFormService.removeById(id);
        }
        Object idsObj = params.get("ids");
        if (idsObj instanceof List) {
            List<?> idsList = (List<?>) idsObj;
            List<Integer> ids = idsList.stream()
                    .map(o -> o instanceof Integer ? (Integer) o : Integer.parseInt(o.toString()))
                    .collect(Collectors.toList());
            systemFormService.removeByIds(ids);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "删除成功");
        return result;
    }
}
