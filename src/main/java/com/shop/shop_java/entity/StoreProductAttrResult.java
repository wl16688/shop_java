package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_product_attr_result")
public class StoreProductAttrResult {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("product_id")
    private Integer productId;

    private String result;

    @TableField("change_time")
    private Integer changeTime;

    private Integer type;
}

