package com.shop.shop_java.controller;

import com.shop.shop_java.common.Result;
import com.shop.shop_java.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    @Autowired
    private SystemAdminService systemAdminService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> loginParam) {
        String account = loginParam.get("account");
        String password = loginParam.get("password");

        if (account == null || password == null) {
            return Result.error(400, "Account and password cannot be empty");
        }

        try {
            String token = systemAdminService.login(account, password);
            return Result.success(token);
        } catch (Exception e) {
            return Result.error(401, e.getMessage());
        }
    }
}
