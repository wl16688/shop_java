package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.StoreProductLabel;
import com.shop.shop_java.mapper.StoreProductLabelMapper;
import com.shop.shop_java.service.StoreProductLabelService;
import org.springframework.stereotype.Service;

@Service
public class StoreProductLabelServiceImpl extends ServiceImpl<StoreProductLabelMapper, StoreProductLabel> implements StoreProductLabelService {
}
