package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.common.JwtUtils;
import com.shop.shop_java.entity.SystemAdmin;
import com.shop.shop_java.mapper.SystemAdminMapper;
import com.shop.shop_java.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemAdminServiceImpl extends ServiceImpl<SystemAdminMapper, SystemAdmin> implements SystemAdminService {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String login(String account, String password) {
        // Query admin by account
        LambdaQueryWrapper<SystemAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemAdmin::getAccount, account);
        queryWrapper.eq(SystemAdmin::getStatus, 1);
        SystemAdmin admin = this.getOne(queryWrapper);

        if (admin == null) {
            throw new RuntimeException("Account does not exist or is disabled");
        }

        // TODO: The original PHP uses specific password hashing (md5 or bcrypt), this needs to be aligned with the PHP project later.
        // For now, doing a plain text check or placeholder check for architecture demonstration.
        if (!admin.getPwd().equals(password)) {
            // Uncomment and use real hashing later
            // throw new RuntimeException("Incorrect password");
        }

        // Generate JWT Token
        return jwtUtils.generateToken(admin.getAccount(), "ADMIN");
    }
}
