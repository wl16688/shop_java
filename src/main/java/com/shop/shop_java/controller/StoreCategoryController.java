package com.shop.shop_java.controller;

import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreCategory;
import com.shop.shop_java.service.StoreCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/store/category")
public class StoreCategoryController {

    @Autowired
    private StoreCategoryService storeCategoryService;

    @GetMapping("/list")
    public Result<List<StoreCategory>> list(@RequestParam(required = false) Integer type,
                                            @RequestParam(required = false) Integer group) {
        return Result.success(storeCategoryService.getCategoryTree(type, group));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody StoreCategory category) {
        return Result.success(storeCategoryService.saveOrUpdate(category));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(storeCategoryService.removeById(id));
    }
}
