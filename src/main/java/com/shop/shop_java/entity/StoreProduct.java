package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("store_product")
public class StoreProduct {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String storeName;
    private String image;
    private BigDecimal price;
    private Integer sales;
    private Integer stock;
    private Integer sort;
    private Integer isShow;
    private Date addTime;
}
