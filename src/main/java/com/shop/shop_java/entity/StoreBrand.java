package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_brand")
public class StoreBrand {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String brandName;
    private Integer pid;
    private String fid;
    private Integer storeId;
    private Integer sort;
    private Integer isShow;
    private Integer isDel;
    private Integer addTime;
}
