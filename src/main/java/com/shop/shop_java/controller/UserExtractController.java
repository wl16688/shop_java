package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.UserExtract;
import com.shop.shop_java.service.UserExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/finance/extract")
public class UserExtractController {

    @Autowired
    private UserExtractService userExtractService;

    @GetMapping("/list")
    public Result<Page<UserExtract>> list(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "15") Integer limit,
                                          @RequestParam(required = false) Integer status) {
        Page<UserExtract> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<UserExtract> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(UserExtract::getStatus, status);
        }
        wrapper.orderByDesc(UserExtract::getId);
        return Result.success(userExtractService.page(pageParam, wrapper));
    }

    @PostMapping("/audit")
    public Result<Boolean> audit(@RequestBody UserExtract extract) {
        // -1 未通过 0 审核中 1 已提现
        return Result.success(userExtractService.updateById(extract));
    }
}
