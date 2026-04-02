package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_community")
public class Community {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer type; // 类型：0:平台1:门店2:用户
    
    private Integer relationId; // 关联平台管理门店ID、用户UID
    
    private Integer contentType; // 内容类型：1：图文2：视频
    
    private String title; // 标题
    
    private String image; // 封面图
    
    private String videoUrl; // 视频地址
    
    private String content; // 内容详情
    
    private Integer likeNum; // 点赞数量
    
    private Integer collectNum; // 收藏数量
    
    private Integer playNum; // 浏览播放数量
    
    private Integer commentNum; // 评论数量
}
