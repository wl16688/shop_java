package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("store_order")
public class StoreOrder {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String orderId;
    private Integer orderType;
    private String realName;
    private BigDecimal payPrice;
    private Integer paid;
    private Integer status;
    private Date addTime;
}
