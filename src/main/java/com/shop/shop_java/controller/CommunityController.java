package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.Community;
import com.shop.shop_java.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/list")
    public Result<Page<Community>> list(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "15") Integer limit,
                                        @RequestParam(required = false) String title) {
        Page<Community> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Community> wrapper = new LambdaQueryWrapper<>();
        
        if (title != null && !title.isEmpty()) {
            wrapper.like(Community::getTitle, title);
        }
        wrapper.orderByDesc(Community::getId);
        return Result.success(communityService.page(pageParam, wrapper));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(communityService.removeById(id));
    }
}
