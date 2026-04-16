package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_user_group")
public class UserGroup {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String groupName;
}
