package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("eb_store_product_reply")
public class StoreProductReply {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private Integer oid;
    private Integer productId;
    private Integer replyScore;
    private Integer productScore;
    private Integer serviceScore;
    private String comment;
    private String pics;
    private Integer addTime;
    private String merchantReplyContent;
    private Integer merchantReplyTime;
    private Integer isDel;
    private Integer isReply;
    private String nickname;
    private String avatar;
}
