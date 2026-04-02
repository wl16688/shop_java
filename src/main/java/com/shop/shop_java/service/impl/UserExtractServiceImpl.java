package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.UserExtract;
import com.shop.shop_java.mapper.UserExtractMapper;
import com.shop.shop_java.service.UserExtractService;
import org.springframework.stereotype.Service;

@Service
public class UserExtractServiceImpl extends ServiceImpl<UserExtractMapper, UserExtract> implements UserExtractService {
}
