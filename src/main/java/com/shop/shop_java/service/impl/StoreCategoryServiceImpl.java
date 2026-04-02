package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.StoreCategory;
import com.shop.shop_java.mapper.StoreCategoryMapper;
import com.shop.shop_java.service.StoreCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreCategoryServiceImpl extends ServiceImpl<StoreCategoryMapper, StoreCategory> implements StoreCategoryService {

    @Override
    public List<StoreCategory> getCategoryTree(Integer type, Integer group) {
        LambdaQueryWrapper<StoreCategory> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(StoreCategory::getType, type);
        }
        if (group != null) {
            wrapper.eq(StoreCategory::getGroup, group); // e.g. 2=商品分类
        }
        wrapper.orderByAsc(StoreCategory::getSort);
        return this.list(wrapper); // Return flat, let frontend build tree
    }
}
