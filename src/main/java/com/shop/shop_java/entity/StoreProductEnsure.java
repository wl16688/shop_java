package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_product_ensure")
public class StoreProductEnsure {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer type;
    private Integer relationId;
    private String name;
    private String image;
    @TableField("`desc`")
    private String desc;
    private Integer sort;
    private Integer status;
    private Integer addTime;
}
