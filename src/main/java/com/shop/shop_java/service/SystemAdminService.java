package com.shop.shop_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.shop_java.entity.SystemAdmin;

public interface SystemAdminService extends IService<SystemAdmin> {
    String login(String account, String password);
}
