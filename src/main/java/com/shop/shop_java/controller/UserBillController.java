package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.UserBill;
import com.shop.shop_java.service.UserBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/finance/bill")
public class UserBillController {

    @Autowired
    private UserBillService userBillService;

    @GetMapping("/list")
    public Result<Page<UserBill>> list(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "15") Integer limit,
                                       @RequestParam(required = false) Integer uid,
                                       @RequestParam(required = false) String category) {
        Page<UserBill> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<UserBill> wrapper = new LambdaQueryWrapper<>();
        
        if (uid != null) {
            wrapper.eq(UserBill::getUid, uid);
        }
        if (category != null && !category.isEmpty()) {
            wrapper.eq(UserBill::getCategory, category);
        }
        wrapper.orderByDesc(UserBill::getId);
        return Result.success(userBillService.page(pageParam, wrapper));
    }
}
