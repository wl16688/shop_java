package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("eb_store_product")
public class StoreProduct {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String storeName;
    private String keyword;
    private String image;
    private BigDecimal price;
    private Integer sales;
    private Integer stock;
    private Integer sort;
    private Integer isShow;
    private Integer isDel;
    private Integer isVerify;
    private Integer isSold;
    private Integer isPolice;
    private String cateId;
    private Integer brandId;
    private String unitName;
    private String code;
    private String barCode;
    private String sliderImage;
    private Integer addTime;
}
