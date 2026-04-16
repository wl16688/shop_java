package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("eb_user")
public class EbUser {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String avatar;
    private String nickname;
    private String phone;
    private Integer level;
    private BigDecimal nowMoney;
    private Integer integral;
    private Integer status;
    private Integer addTime;
}
