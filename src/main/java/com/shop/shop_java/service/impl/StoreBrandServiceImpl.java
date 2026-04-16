package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.StoreBrand;
import com.shop.shop_java.mapper.StoreBrandMapper;
import com.shop.shop_java.service.StoreBrandService;
import org.springframework.stereotype.Service;

@Service
public class StoreBrandServiceImpl extends ServiceImpl<StoreBrandMapper, StoreBrand> implements StoreBrandService {
}
