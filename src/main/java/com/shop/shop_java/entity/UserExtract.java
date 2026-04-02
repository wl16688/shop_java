package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName("eb_user_extract")
public class UserExtract {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer uid;
    
    private String realName; // 名称
    
    private String extractType; // bank = 银行卡 alipay = 支付宝wx=微信
    
    private String bankCode; // 银行卡
    
    private String bankAddress; // 开户地址
    
    private String alipayCode; // 支付宝账号
    
    private BigDecimal extractPrice; // 提现金额
    
    private BigDecimal extractFee; // 手续费金额
    
    private String mark;
    
    private BigDecimal balance;
    
    private String failMsg; // 无效原因
    
    private Integer failTime;
    
    private Integer addTime; // 添加时间
    
    private Integer status; // -1 未通过 0 审核中 1 已提现
}
