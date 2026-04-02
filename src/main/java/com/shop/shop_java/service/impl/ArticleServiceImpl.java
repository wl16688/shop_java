package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.Article;
import com.shop.shop_java.mapper.ArticleMapper;
import com.shop.shop_java.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
