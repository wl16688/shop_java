package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.SystemStore;
import com.shop.shop_java.mapper.SystemStoreMapper;
import com.shop.shop_java.service.SystemStoreService;
import org.springframework.stereotype.Service;

@Service
public class SystemStoreServiceImpl extends ServiceImpl<SystemStoreMapper, SystemStore> implements SystemStoreService {
}
