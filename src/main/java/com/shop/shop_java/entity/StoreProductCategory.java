package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_product_category")
public class StoreProductCategory {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private Integer type;
    private Integer relationId;
    private String cateName;
    private String path;
    private Integer level;
    private String pic;
    private String bigPic;
    private String advPic;
    private String advLink;
    private Integer sort;
    private Integer isShow;
    private Integer addTime;
}
