package com.shop.shop_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.shop_java.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
