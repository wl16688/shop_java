package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.StoreCart;
import com.shop.shop_java.mapper.StoreCartMapper;
import com.shop.shop_java.service.StoreCartService;
import org.springframework.stereotype.Service;

@Service
public class StoreCartServiceImpl extends ServiceImpl<StoreCartMapper, StoreCart> implements StoreCartService {
}
