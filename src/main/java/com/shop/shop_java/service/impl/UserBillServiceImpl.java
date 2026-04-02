package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.UserBill;
import com.shop.shop_java.mapper.UserBillMapper;
import com.shop.shop_java.service.UserBillService;
import org.springframework.stereotype.Service;

@Service
public class UserBillServiceImpl extends ServiceImpl<UserBillMapper, UserBill> implements UserBillService {
}
