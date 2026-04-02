package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName("eb_user_bill")
public class UserBill {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer uid; // 用户uid
    
    private String linkId; // 关联id
    
    private Integer pm; // 0 = 支出 1 = 获得
    
    private String title; // 账单标题
    
    private String category; // 明细种类
    
    private String type; // 明细类型
    
    private BigDecimal number; // 明细数字
    
    private BigDecimal balance; // 剩余
    
    private String mark; // 备注
    
    private Integer addTime; // 添加时间
    
    private Integer status; // 0 = 带确定 1 = 有效 -1 = 无效
}
