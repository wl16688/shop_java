package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_product_rule")
public class StoreProductRule {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer type;
    private Integer relationId;
    private String ruleName;
    private String ruleValue;
}
