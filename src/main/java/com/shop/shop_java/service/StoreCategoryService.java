package com.shop.shop_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.shop_java.entity.StoreCategory;
import java.util.List;

public interface StoreCategoryService extends IService<StoreCategory> {
    List<StoreCategory> getCategoryTree(Integer type, Integer group);
}
