package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName("eb_store_coupon_issue")
public class StoreCoupon {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer type; // 0通用 1品类券 2商品券 3品牌
    private Integer receiveType; // 1手动领取 2新人券 3赠送券
    private Integer couponType; // 1满减券 2折扣券
    private String couponTitle;
    private Integer startTime;
    private Integer endTime;
    private Integer totalCount;
    private Integer remainCount;
    private BigDecimal couponPrice;
    private BigDecimal useMinPrice;
    private Integer status; // 1有效 0无效
    private Integer isDel;
    private Integer addTime;
}
