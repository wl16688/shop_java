package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreProductCategory;
import com.shop.shop_java.service.StoreProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/store/productCategory")
public class StoreProductCategoryController {

    @Autowired
    private StoreProductCategoryService storeProductCategoryService;

    @GetMapping("/list")
    public Result<List<StoreProductCategory>> list(@RequestParam(required = false) String keyword,
                                                   @RequestParam(required = false) Integer isShow) {
        LambdaQueryWrapper<StoreProductCategory> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.like(StoreProductCategory::getCateName, keyword);
        }
        if (isShow != null) {
            wrapper.eq(StoreProductCategory::getIsShow, isShow);
        }
        
        wrapper.orderByAsc(StoreProductCategory::getSort).orderByAsc(StoreProductCategory::getId);
        return Result.success(storeProductCategoryService.list(wrapper));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody StoreProductCategory category) {
        if (category.getPid() == null) {
            category.setPid(0);
        }
        
        // 计算 path 和 level
        if (category.getPid() == 0) {
            category.setLevel(1);
            category.setPath("/0/");
        } else {
            StoreProductCategory parent = storeProductCategoryService.getById(category.getPid());
            if (parent != null) {
                category.setLevel(parent.getLevel() + 1);
                category.setPath(parent.getPath() + parent.getId() + "/");
            } else {
                category.setLevel(1);
                category.setPath("/0/");
                category.setPid(0);
            }
        }
        
        if (category.getAddTime() == null) {
            category.setAddTime((int)(System.currentTimeMillis() / 1000));
        }
        return Result.success(storeProductCategoryService.saveOrUpdate(category));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(storeProductCategoryService.removeById(id));
    }
}
