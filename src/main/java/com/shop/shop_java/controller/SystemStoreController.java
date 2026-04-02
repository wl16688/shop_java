package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.SystemStore;
import com.shop.shop_java.service.SystemStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/system/store")
public class SystemStoreController {

    @Autowired
    private SystemStoreService systemStoreService;

    @GetMapping("/list")
    public Result<Page<SystemStore>> list(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "15") Integer limit) {
        Page<SystemStore> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<SystemStore> wrapper = new LambdaQueryWrapper<>();
        // wrapper.eq(SystemStore::getIsDel, 0);
        return Result.success(systemStoreService.page(pageParam, wrapper));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SystemStore store) {
        return Result.success(systemStoreService.saveOrUpdate(store));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(systemStoreService.removeById(id));
    }
}
