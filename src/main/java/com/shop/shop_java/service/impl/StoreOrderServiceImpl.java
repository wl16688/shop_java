package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.StoreOrder;
import com.shop.shop_java.mapper.StoreOrderMapper;
import com.shop.shop_java.service.StoreOrderService;
import org.springframework.stereotype.Service;

@Service
public class StoreOrderServiceImpl extends ServiceImpl<StoreOrderMapper, StoreOrder> implements StoreOrderService {
}
