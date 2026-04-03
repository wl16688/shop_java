package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_category")
public class StoreCategory {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer pid; // 上级id
    
    private Integer type; // 类型：0平台 2:供应商
    
    private Integer relationId; // 门店、供应商id
    
    private Integer ownerId; // 所属人
    
    private String name; // 分类名称
    
    private Integer sort; // 排序
    
        @com.baomidou.mybatisplus.annotation.TableField("`group`")
    private Integer group; // 分类类型
    
    private String other; // 其他参数
    
    private Integer isShow; // 是否显示
}
