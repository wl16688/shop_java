package com.shop.shop_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("eb_store_product")
public class StoreProduct {
    @TableId(type = IdType.AUTO)
    private Integer id; // 商品ID
    private Integer pid; // 关联平台商品ID
    private Integer type; // 商品归属：0=平台，1=门店，2=供应商
    private Integer productType; // 商品类型：0=普通商品，1=卡密，2=优惠券，3=虚拟商品，4=次卡商品
    private Integer relationId; // 关联门店/供应商ID
    private Integer merId; // 商户ID(平台默认0)
    private String storeName; // 商品名称
    private String keyword; // 商品关键字
    private String image; // 商品主图
    private BigDecimal price; // 商品售价
    private Integer sales; // 销量
    private Integer stock; // 库存
    private Integer sort; // 排序权重(越大越靠前)
    private Integer isShow; // 是否上架：0=下架(仓库中)，1=上架(销售中)
    private Integer isDel; // 是否删除：0=正常，1=已删除(放入回收站)
    private Integer isVerify; // 审核状态：1=审核通过，0=待审核，-1=审核未通过，-2=强制下架
    private Integer isSold; // 是否售罄：0=正常，1=已售罄
    private Integer isPolice; // 是否触发库存预警：0=正常，1=预警
    private String cateId; // 商品分类ID(支持逗号分隔多个分类)
    private Integer brandId; // 商品品牌ID
    private String unitName; // 商品单位(如：件、个、瓶等)
    private String code; // 商品编码(商家自定义SKU等)
    private String barCode; // 商品条形码(扫描码)
    private String sliderImage; // 商品轮播图(多张图的JSON数组或逗号分隔)
    private Integer addTime; // 添加时间(Unix时间戳)
}
