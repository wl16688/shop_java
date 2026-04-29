package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.StoreProduct;
import com.shop.shop_java.entity.StoreProductAttr;
import com.shop.shop_java.entity.StoreProductAttrResult;
import com.shop.shop_java.entity.StoreProductAttrValue;
import com.shop.shop_java.service.StoreProductAttrResultService;
import com.shop.shop_java.service.StoreProductAttrService;
import com.shop.shop_java.service.StoreProductAttrValueService;
import com.shop.shop_java.service.StoreProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@RestController
@RequestMapping("/api/admin/store/product")
public class StoreProductSpecController {
    @Autowired
    private StoreProductAttrService productAttrService;
    @Autowired
    private StoreProductAttrResultService productAttrResultService;
    @Autowired
    private StoreProductAttrValueService productAttrValueService;
    @Autowired
    private StoreProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/generate_attr/{id}/{type}")
    public Result<Map<String, Object>> generateAttr(@PathVariable Integer id,
                                                    @PathVariable Integer type,
                                                    @RequestBody Map<String, Object> body) {
        List<Map<String, Object>> attrs = castList(body.get("attrs"));
        Integer productType = castInt(body.get("product_type"), 0);
        Map<String, Object> info = buildAttrInfo(id, type, productType, attrs);
        Map<String, Object> data = new HashMap<>();
        data.put("info", info);
        return Result.success(data);
    }

    @GetMapping("/attrs/{id}")
    public Result<Map<String, Object>> getAttrs(@PathVariable Integer id,
                                                @RequestParam(defaultValue = "0") Integer type) {
        StoreProductAttrResult result = productAttrResultService.getOne(new LambdaQueryWrapper<StoreProductAttrResult>()
                .eq(StoreProductAttrResult::getProductId, id)
                .eq(StoreProductAttrResult::getType, type), false);
        if (result != null && result.getResult() != null && !result.getResult().isEmpty()) {
            Map<String, Object> parsed;
            try {
                parsed = objectMapper.readValue(result.getResult(), new TypeReference<Map<String, Object>>() {});
            } catch (Exception e) {
                parsed = new HashMap<>();
            }
            Map<String, Object> data = new HashMap<>();
            data.put("info", parsed);
            return Result.success(data);
        }

        StoreProduct product = productService.getById(id);
        Integer productType = product != null && product.getProductType() != null ? product.getProductType() : 0;
        List<StoreProductAttr> list = productAttrService.list(new LambdaQueryWrapper<StoreProductAttr>()
                .eq(StoreProductAttr::getProductId, id)
                .eq(StoreProductAttr::getType, type)
                .orderByAsc(StoreProductAttr::getId));
        List<Map<String, Object>> attrs = new ArrayList<>();
        for (StoreProductAttr a : list) {
            Map<String, Object> item = new HashMap<>();
            item.put("value", a.getAttrName());
            List<Map<String, Object>> detail = new ArrayList<>();
            if (a.getAttrValues() != null && !a.getAttrValues().isEmpty()) {
                for (String v : a.getAttrValues().split(",")) {
                    String vv = v == null ? "" : v.trim();
                    if (vv.isEmpty()) continue;
                    Map<String, Object> dv = new HashMap<>();
                    dv.put("value", vv);
                    dv.put("pic", "");
                    detail.add(dv);
                }
            }
            item.put("detail", detail);
            attrs.add(item);
        }
        Map<String, Object> info = buildAttrInfo(id, type, productType, attrs);
        Map<String, Object> data = new HashMap<>();
        data.put("info", info);
        return Result.success(data);
    }

    @PostMapping("/specs/save")
    public Result<Boolean> saveSpecs(@RequestBody Map<String, Object> body) {
        Integer productId = castInt(body.get("productId"), null);
        if (productId == null) {
            productId = castInt(body.get("product_id"), null);
        }
        if (productId == null) {
            return Result.error(400, "productId不能为空");
        }
        Integer productType = castInt(body.get("productType"), 0);
        if (body.containsKey("product_type")) {
            productType = castInt(body.get("product_type"), productType);
        }
        Integer type = castInt(body.get("type"), 0);

        List<Map<String, Object>> items = castList(body.get("items"));
        List<Map<String, Object>> value = castList(body.get("attrs"));
        if (value.isEmpty()) {
            value = castList(body.get("value"));
        }

        productAttrService.remove(new LambdaQueryWrapper<StoreProductAttr>()
                .eq(StoreProductAttr::getProductId, productId)
                .eq(StoreProductAttr::getType, type));
        List<StoreProductAttr> attrEntities = new ArrayList<>();
        for (Map<String, Object> item : items) {
            String name = castString(item.get("value"));
            if (name == null || name.trim().isEmpty()) continue;
            List<Map<String, Object>> detail = castList(item.get("detail"));
            List<String> values = new ArrayList<>();
            for (Map<String, Object> dv : detail) {
                String v = castString(dv.get("value"));
                if (v != null && !v.trim().isEmpty()) values.add(v.trim());
            }
            StoreProductAttr a = new StoreProductAttr();
            a.setProductId(productId);
            a.setType(type);
            a.setAttrName(name.trim());
            a.setAttrValues(String.join(",", values));
            attrEntities.add(a);
        }
        if (!attrEntities.isEmpty()) {
            productAttrService.saveBatch(attrEntities);
        }

        Map<String, Object> info = buildAttrInfo(productId, type, productType, items);
        productAttrResultService.remove(new LambdaQueryWrapper<StoreProductAttrResult>()
                .eq(StoreProductAttrResult::getProductId, productId)
                .eq(StoreProductAttrResult::getType, type));
        StoreProductAttrResult r = new StoreProductAttrResult();
        r.setProductId(productId);
        r.setType(type);
        r.setChangeTime((int) (System.currentTimeMillis() / 1000));
        try {
            r.setResult(objectMapper.writeValueAsString(info));
        } catch (Exception e) {
            r.setResult("{}");
        }
        productAttrResultService.save(r);

        Map<String, StoreProductAttrValue> old = new HashMap<>();
        List<StoreProductAttrValue> oldList = productAttrValueService.list(new LambdaQueryWrapper<StoreProductAttrValue>()
                .eq(StoreProductAttrValue::getProductId, productId)
                .eq(StoreProductAttrValue::getType, type));
        for (StoreProductAttrValue o : oldList) {
            if (o.getSku() != null) old.put(o.getSku(), o);
        }

        productAttrValueService.remove(new LambdaQueryWrapper<StoreProductAttrValue>()
                .eq(StoreProductAttrValue::getProductId, productId)
                .eq(StoreProductAttrValue::getType, type));

        List<StoreProductAttrValue> toSave = new ArrayList<>();
        for (Map<String, Object> row : value) {
            StoreProductAttrValue v = new StoreProductAttrValue();
            v.setProductId(productId);
            v.setProductType(productType);
            v.setType(type);
            v.setSku(optSku(row));
            v.setImage(castString(row.getOrDefault("pic", row.get("image"))));
            v.setPrice(castBigDecimal(row.get("price")));
            v.setSettlePrice(castBigDecimal(row.getOrDefault("settle_price", row.get("settlePrice"))));
            v.setCost(castBigDecimal(row.get("cost")));
            v.setOtPrice(castBigDecimal(row.getOrDefault("ot_price", row.get("otPrice"))));
            v.setVipPrice(castBigDecimal(row.getOrDefault("vip_price", row.get("vipPrice"))));
            v.setStock(castInt(row.get("stock"), 0));
            v.setBarCode(castString(row.getOrDefault("bar_code", row.get("barCode"))));
            v.setCode(castString(row.get("code")));
            v.setWeight(castBigDecimal(row.get("weight")));
            v.setVolume(castBigDecimal(row.get("volume")));
            v.setBrokerage(castBigDecimal(row.get("brokerage")));
            v.setBrokerageTwo(castBigDecimal(row.getOrDefault("brokerage_two", row.get("brokerageTwo"))));
            v.setLevelPrice(castString(row.getOrDefault("level_price", row.get("levelPrice"))));
            v.setIsDefaultSelect(castInt(row.getOrDefault("is_default_select", row.get("isDefaultSelect")), 0));
            v.setIsShow(castInt(row.getOrDefault("is_show", row.get("isShow")), 1));
            v.setSort(castInt(row.getOrDefault("sort", 0), 0));
            String unique = castString(row.get("unique"));
            if (unique == null || unique.isEmpty()) {
                StoreProductAttrValue existed = old.get(v.getSku());
                unique = existed != null && existed.getUnique() != null ? existed.getUnique() : createAttrUnique(productId, v.getSku());
            }
            v.setUnique(unique);
            toSave.add(v);
        }
        if (!toSave.isEmpty()) {
            productAttrValueService.saveBatch(toSave);
        }

        StoreProduct product = productService.getById(productId);
        if (product != null) {
            product.setSpecType(1);
            product.setProductType(productType);
            int totalStock = 0;
            BigDecimal minPrice = null;
            String defaultSku = null;
            for (StoreProductAttrValue v : toSave) {
                if (v.getIsShow() != null && v.getIsShow() == 1) {
                    totalStock += v.getStock() == null ? 0 : v.getStock();
                }
                if (v.getPrice() != null) {
                    if (minPrice == null || v.getPrice().compareTo(minPrice) < 0) minPrice = v.getPrice();
                }
                if (v.getIsDefaultSelect() != null && v.getIsDefaultSelect() == 1) {
                    defaultSku = v.getSku();
                }
            }
            if (defaultSku == null && !toSave.isEmpty()) defaultSku = toSave.get(0).getSku();
            product.setDefaultSku(defaultSku == null ? "" : defaultSku);
            product.setStock(totalStock);
            if (minPrice != null) product.setPrice(minPrice);
            productService.updateById(product);
        }

        return Result.success(true);
    }

    private Map<String, Object> buildAttrInfo(Integer productId, Integer type, Integer productType, List<Map<String, Object>> attrs) {
        List<Map<String, Object>> normalized = normalizeAttrs(attrs);
        List<List<String>> dims = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (Map<String, Object> item : normalized) {
            titles.add(castString(item.get("value")));
            List<Map<String, Object>> detail = castList(item.get("detail"));
            List<String> values = new ArrayList<>();
            for (Map<String, Object> dv : detail) {
                String v = castString(dv.get("value"));
                if (v != null && !v.trim().isEmpty()) values.add(v.trim());
            }
            dims.add(values);
        }

        Map<String, StoreProductAttrValue> existed = new HashMap<>();
        if (productId != null && productId > 0) {
            List<StoreProductAttrValue> list = productAttrValueService.list(new LambdaQueryWrapper<StoreProductAttrValue>()
                    .eq(StoreProductAttrValue::getProductId, productId)
                    .eq(StoreProductAttrValue::getType, type));
            for (StoreProductAttrValue v : list) {
                if (v.getSku() != null) existed.put(v.getSku(), v);
            }
        }

        List<Map<String, Object>> header = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            Map<String, Object> h = new HashMap<>();
            h.put("title", titles.get(i));
            h.put("slot", "value" + (i + 1));
            h.put("align", "center");
            h.put("minWidth", 130);
            header.add(h);
        }
        header.add(column("图片", "pic", 80));
        header.add(column("售价", "price", 120));
        header.add(column("成本价", "cost", 140));
        header.add(column("划线价", "ot_price", 140));
        header.add(column("库存", "stock", 140));
        header.add(column("商品条形码", "bar_code", 140));
        header.add(column("商品编码", "code", 140));
        if (productType != null && productType == 0) {
            header.add(column("重量(KG)", "weight", 140));
            header.add(column("体积(m³)", "volume", 140));
        }
        header.add(column("操作", "action", 70));

        List<List<String>> combos = cartesian(dims);
        List<Map<String, Object>> value = new ArrayList<>();
        for (List<String> combo : combos) {
            String suk = String.join(",", combo);
            Map<String, Object> row = new HashMap<>();
            for (int i = 0; i < combo.size(); i++) {
                row.put("value" + (i + 1), combo.get(i));
            }
            row.put("detail", combo);
            row.put("sku", suk);
            StoreProductAttrValue old = existed.get(suk);
            row.put("unique", old != null ? old.getUnique() : createAttrUnique(productId == null ? 0 : productId, suk));
            row.put("pic", old != null ? optString(old.getImage()) : "");
            row.put("price", old != null ? optBigDecimal(old.getPrice()) : BigDecimal.ZERO);
            row.put("cost", old != null ? optBigDecimal(old.getCost()) : BigDecimal.ZERO);
            row.put("ot_price", old != null ? optBigDecimal(old.getOtPrice()) : BigDecimal.ZERO);
            row.put("stock", old != null ? (old.getStock() == null ? 0 : old.getStock()) : 0);
            row.put("bar_code", old != null ? optString(old.getBarCode()) : "");
            row.put("code", old != null ? optString(old.getCode()) : "");
            row.put("weight", old != null ? optBigDecimal(old.getWeight()) : BigDecimal.ZERO);
            row.put("volume", old != null ? optBigDecimal(old.getVolume()) : BigDecimal.ZERO);
            row.put("brokerage", old != null ? optBigDecimal(old.getBrokerage()) : BigDecimal.ZERO);
            row.put("brokerage_two", old != null ? optBigDecimal(old.getBrokerageTwo()) : BigDecimal.ZERO);
            row.put("vip_price", old != null ? optBigDecimal(old.getVipPrice()) : BigDecimal.ZERO);
            row.put("level_price", old != null ? optString(old.getLevelPrice()) : "");
            row.put("is_default_select", old != null && old.getIsDefaultSelect() != null ? old.getIsDefaultSelect() : 0);
            row.put("is_show", old != null && old.getIsShow() != null ? old.getIsShow() : 1);
            row.put("sort", old != null && old.getSort() != null ? old.getSort() : 0);
            value.add(row);
        }

        Map<String, Object> info = new HashMap<>();
        info.put("attr", normalized);
        info.put("value", value);
        info.put("header", header);
        info.put("product_type", productType);
        return info;
    }

    private Map<String, Object> column(String title, String slot, int minWidth) {
        Map<String, Object> h = new HashMap<>();
        h.put("title", title);
        h.put("slot", slot);
        h.put("align", "center");
        h.put("minWidth", minWidth);
        return h;
    }

    private List<Map<String, Object>> normalizeAttrs(List<Map<String, Object>> attrs) {
        List<Map<String, Object>> normalized = new ArrayList<>();
        for (Map<String, Object> raw : attrs) {
            if (raw == null) continue;
            String name = castString(raw.get("value"));
            if (name == null || name.trim().isEmpty()) continue;
            Map<String, Object> item = new HashMap<>();
            item.put("value", name.trim());
            List<Map<String, Object>> detail = castList(raw.get("detail"));
            List<Map<String, Object>> normDetail = new ArrayList<>();
            for (Object o : detail) {
                if (o instanceof Map) {
                    Map<String, Object> m = (Map<String, Object>) o;
                    String v = castString(m.get("value"));
                    if (v == null || v.trim().isEmpty()) continue;
                    Map<String, Object> dv = new HashMap<>();
                    dv.put("value", v.trim());
                    dv.put("pic", castString(m.getOrDefault("pic", "")));
                    normDetail.add(dv);
                } else {
                    String v = castString(o);
                    if (v == null || v.trim().isEmpty()) continue;
                    Map<String, Object> dv = new HashMap<>();
                    dv.put("value", v.trim());
                    dv.put("pic", "");
                    normDetail.add(dv);
                }
            }
            item.put("detail", normDetail);
            normalized.add(item);
        }
        return normalized;
    }

    private List<List<String>> cartesian(List<List<String>> dims) {
        List<List<String>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (List<String> dim : dims) {
            List<List<String>> next = new ArrayList<>();
            for (List<String> prefix : result) {
                for (String v : dim) {
                    List<String> n = new ArrayList<>(prefix);
                    n.add(v);
                    next.add(n);
                }
            }
            result = next;
        }
        return result;
    }

    private String createAttrUnique(int productId, String sku) {
        try {
            String raw = productId + sku + UUID.randomUUID();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.substring(12, 20);
        } catch (Exception e) {
            return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        }
    }

    private String optSku(Map<String, Object> row) {
        String sku = castString(row.get("sku"));
        if (sku == null) sku = castString(row.get("suk"));
        if (sku == null) sku = castString(row.get("values"));
        return sku == null ? "" : sku;
    }

    private String optString(String s) {
        return s == null ? "" : s;
    }

    private BigDecimal optBigDecimal(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    private Integer castInt(Object v, Integer def) {
        if (v == null) return def;
        if (v instanceof Number) return ((Number) v).intValue();
        try {
            String s = String.valueOf(v).trim();
            if (s.isEmpty()) return def;
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    private String castString(Object v) {
        if (v == null) return null;
        return String.valueOf(v);
    }

    private BigDecimal castBigDecimal(Object v) {
        if (v == null) return BigDecimal.ZERO;
        if (v instanceof BigDecimal) return (BigDecimal) v;
        if (v instanceof Number) return new BigDecimal(String.valueOf(v));
        try {
            String s = String.valueOf(v).trim();
            if (s.isEmpty()) return BigDecimal.ZERO;
            return new BigDecimal(s);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> castList(Object v) {
        if (v == null) return new ArrayList<>();
        if (v instanceof List) {
            List<?> list = (List<?>) v;
            List<Map<String, Object>> res = new ArrayList<>();
            for (Object o : list) {
                if (o instanceof Map) res.add((Map<String, Object>) o);
            }
            return res;
        }
        try {
            return objectMapper.readValue(String.valueOf(v), new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
