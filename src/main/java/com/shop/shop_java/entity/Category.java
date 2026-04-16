package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private Integer type;
    private Integer relationId;
    private Integer ownerId;
    private String name;
    private Integer sort;
    @TableField("`group`")
    private Integer group;
    private String other;
    private Integer isShow;
    private Integer addTime;
    private Integer integralMin;
    private Integer integralMax;
}
