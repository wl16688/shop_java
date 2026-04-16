package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.entity.UserGroup;
import com.shop.shop_java.mapper.UserGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/user/group")
public class UserGroupController {

    @Autowired
    private UserGroupMapper userGroupMapper;

    @GetMapping
    public Map<String, Object> getList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) String keyword) {

        Page<UserGroup> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<UserGroup> queryWrapper = new LambdaQueryWrapper<>();

        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(UserGroup::getGroupName, keyword);
        }
        
        userGroupMapper.selectPage(pageParam, queryWrapper);

        List<Map<String, Object>> list = new ArrayList<>();
        for (UserGroup group : pageParam.getRecords()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", group.getId());
            item.put("group_name", group.getGroupName());
            // count can be added if joined with user table
            item.put("count", 0);
            item.put("create_time", "");
            list.add(item);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", pageParam.getTotal());

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("data", data);
        return response;
    }
}
