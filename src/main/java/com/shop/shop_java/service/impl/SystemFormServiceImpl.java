package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.SystemForm;
import com.shop.shop_java.mapper.SystemFormMapper;
import com.shop.shop_java.service.SystemFormService;
import org.springframework.stereotype.Service;

@Service
public class SystemFormServiceImpl extends ServiceImpl<SystemFormMapper, SystemForm> implements SystemFormService {
}
