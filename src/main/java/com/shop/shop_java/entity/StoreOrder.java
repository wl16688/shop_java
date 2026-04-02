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
    
    private Integer type; // 类型 0:普通、1：秒杀、2:砍价、3:拼团、4:积分、5:套餐、6:预售、7：新人、8抽奖
    
    private Integer pid; // 父类订单id
    
    private String orderId; // 订单号
    
    private String tradeNo; // 支付宝订单号
    
    private Integer supplierId; // 供应商ID
    
    private Integer storeId; // 门店id
    
    private Integer uid; // 用户id
    
    private String realName; // 用户姓名
    
    private String userPhone; // 用户电话
    
    private String province; // 用户省份
    
    private String userAddress; // 详细地址
    
    private String userLocation; // 用户地址定位
    
    private String cartId; // 购物车ids
    
    private Integer pinkId; // 拼团id 0没有拼团
    
    private Integer activityId; // 活动商品ID
    
    private String activityAppend; // 活动附加字段
    
    private BigDecimal freightPrice; // 运费金额
    
    private Integer totalNum; // 订单商品总数
    
    private BigDecimal totalPrice; // 订单总价
    
    private BigDecimal payPrice; // 实际支付金额
    
    private BigDecimal payPostage; // 支付邮费
    
    private BigDecimal deductionPrice; // 抵扣金额
    
    private Integer couponId; // 优惠券id
    
    private BigDecimal couponPrice; // 优惠券金额
    
    private Integer paid; // 支付状态
    
    private Integer payTime; // 支付时间
    
    private String payType; // 支付方式
    
    private Integer addTime; // 创建时间
    
    private Integer status; // 订单状态（0：待发货；1：待收货；2：已收货；3：待评价；-1：已退款）
    
    private Integer refundStatus; // 退款状态 0 未退款 1 申请中 2 已退款
    
    private String mark; // 备注
    
    private Integer isDel; // 是否删除
}
