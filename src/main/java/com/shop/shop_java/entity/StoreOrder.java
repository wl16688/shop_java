package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("eb_store_order")
public class StoreOrder {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String orderId;
    private String tradeNo;
    private Integer type;
    private String realName;
    private String userPhone;
    private BigDecimal totalPrice;
    private BigDecimal payPrice;
    private String payType;
    private Integer paid;
    private Integer status;
    private String deliveryName;
    private String deliveryId;
    private String deliveryType;
    private Integer addTime;
    private Integer isDel;
}
