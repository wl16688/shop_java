package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_system_admin")
public class SystemAdmin {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String account;

    private String pwd;

    private String realName;

    private String phone;

    private String roles;

    private Integer status;

    private Integer adminType;

    private Integer relationId;

    private String headPic;

    private String lastIp;

    private Integer lastTime;

    private Integer addTime;

    private Integer loginCount;

    private Integer level;
}
