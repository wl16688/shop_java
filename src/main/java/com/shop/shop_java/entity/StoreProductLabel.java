package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_product_label")
public class StoreProductLabel {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer type;
    private Integer relationId;
    private Integer labelCate;
    private String labelName;
    private Integer styleType;
    private String color;
    private String bgColor;
    private String borderColor;
    private String icon;
    private Integer isShow;
    private Integer status;
    private Integer sort;
    private Integer addTime;
}
