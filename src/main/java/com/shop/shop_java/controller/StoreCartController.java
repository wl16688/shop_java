package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreCart;
import com.shop.shop_java.service.StoreCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/store/cart")
public class StoreCartController {

    @Autowired
    private StoreCartService storeCartService;

    @GetMapping("/list")
    public Result<Page<StoreCart>> list(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "15") Integer limit,
                                        @RequestParam(required = false) Integer uid) {
        Page<StoreCart> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<StoreCart> wrapper = new LambdaQueryWrapper<>();
        // wrapper.eq(StoreCart::getIsDel, 0); // 不显示已删除
        // wrapper.eq(StoreCart::getIsPay, 0); // 通常后台只查看未购买的遗留购物车记录或全部
        
        if (uid != null) {
            wrapper.eq(StoreCart::getUid, uid);
        }
        wrapper.orderByDesc(StoreCart::getId);
        return Result.success(storeCartService.page(pageParam, wrapper));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(storeCartService.removeById(id));
    }
}
