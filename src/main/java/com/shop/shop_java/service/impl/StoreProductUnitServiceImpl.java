package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.StoreProductUnit;
import com.shop.shop_java.mapper.StoreProductUnitMapper;
import com.shop.shop_java.service.StoreProductUnitService;
import org.springframework.stereotype.Service;

@Service
public class StoreProductUnitServiceImpl extends ServiceImpl<StoreProductUnitMapper, StoreProductUnit> implements StoreProductUnitService {
}
