package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_system_form")
public class SystemForm {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String version;
    private String name;
    private String coverImage;
    private String value;
    private String defaultValue;
    private Integer status;
    private Integer isDel;
    private Integer updateTime;
    private Integer addTime;
}
