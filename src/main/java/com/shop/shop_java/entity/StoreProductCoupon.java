package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_product_coupon")
public class StoreProductCoupon {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("product_id")
    private Integer productId;

    @TableField("issue_coupon_id")
    private Integer issueCouponId;

    @TableField("add_time")
    private Integer addTime;

    private String title;
}

