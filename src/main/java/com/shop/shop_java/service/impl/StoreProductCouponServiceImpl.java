package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.StoreProductCoupon;
import com.shop.shop_java.mapper.StoreProductCouponMapper;
import com.shop.shop_java.service.StoreProductCouponService;
import org.springframework.stereotype.Service;

@Service
public class StoreProductCouponServiceImpl extends ServiceImpl<StoreProductCouponMapper, StoreProductCoupon> implements StoreProductCouponService {
}

