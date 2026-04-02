package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_system_store")
public class SystemStore {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer erpShopId; // EPR门店id
    
    private String name; // 门店名称
    
    private String introduction; // 简介
    
    private String phone; // 手机号码
    
    private String address; // 省市区
    
    private Integer province; // 省ID
    
    private Integer city; // 市ID
    
    private Integer area; // 区ID
    
    private Integer street; // 街道ID
    
    private String detailedAddress; // 详细地址
    
    private String image; // 门店logo
    
    private String latitude; // 纬度
    
    private String longitude; // 经度
    
    private String validTime; // 核销有效日期
    
    private String dayTime; // 每日营业开关时间
    
    private Integer isShow; // 是否显示
    
    private Integer isDel; // 是否删除
}
