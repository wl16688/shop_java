package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("eb_store_product_attr_value")
public class StoreProductAttrValue {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("product_id")
    private Integer productId;
    private Integer productType;
    @TableField("suk")
    private String sku;
    private String unique;
    private String image;
    private BigDecimal price;
    @TableField("vip_price")
    private BigDecimal vipPrice;
    private BigDecimal cost;
    @TableField("settle_price")
    private BigDecimal settlePrice;
    @TableField("ot_price")
    private BigDecimal otPrice;
    private Integer stock;
    @TableField("bar_code")
    private String barCode;
    private BigDecimal weight;
    private BigDecimal volume;
    private BigDecimal brokerage;
    @TableField("brokerage_two")
    private BigDecimal brokerageTwo;
    @TableField("level_price")
    private String levelPrice;
    @TableField("is_default_select")
    private Integer isDefaultSelect;
    @TableField("is_show")
    private Integer isShow;
    private Integer sort;
    @TableField("type")
    private Integer type;
    private String code;
}
