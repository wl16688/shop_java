package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_cart")
public class StoreCart {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer uid; // 用户ID
    
    private String touristUid; // 游客uid
    
    private Integer type; // 类型 0:普通、1：秒杀、2:砍价、3:拼团
    
    private Integer productId; // 商品ID
    
    private Integer productType; // 商品类型
    
    private Integer activityId; // 活动商品ID
    
    private Integer storeId; // 门店id
    
    private Integer staffId; // 店员id
    
    private String productAttrUnique; // 商品属性
    
    private Integer cartNum; // 商品数量
    
    private Integer addTime; // 添加时间
    
    private Integer isPay; // 0 = 未购买 1 = 已购买
    
    private Integer isDel; // 是否删除
    
    private Integer isNew; // 是否为立即购买
}
