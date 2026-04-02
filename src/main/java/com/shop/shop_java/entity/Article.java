package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String cid; // 分类id
    
    private String title; // 文章标题
    
    private String author; // 文章作者
    
    private String imageInput; // 文章图片
    
    private String synopsis; // 文章简介
    
    private String shareTitle; // 文章分享标题
    
    private String shareSynopsis; // 文章分享简介
    
    private String visit; // 浏览次数
    
    private Integer likes; // 点赞量
    
    private Integer sort; // 排序
    
    private String url; // 原文链接
    
    private Integer status; // 状态
    
    private String addTime; // 添加时间
    
    private Integer hide; // 是否隐藏
    
    private Integer adminId; // 管理员id
    
    private Integer merId; // 商户id
    
    private Integer isHot; // 是否热门(小程序)
    
    private Integer isBanner; // 是否轮播图(小程序)
}
