package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.entity.StoreProductReply;
import com.shop.shop_java.service.StoreProductReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/store/product/reply")
public class StoreProductReplyController {

    @Autowired
    private StoreProductReplyService replyService;

    @GetMapping("/list")
    public Map<String, Object> getList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer isReply) {

        LambdaQueryWrapper<StoreProductReply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StoreProductReply::getIsDel, 0); // 只查未删除的

        if (isReply != null && isReply != -1) {
            wrapper.eq(StoreProductReply::getIsReply, isReply);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(StoreProductReply::getComment, keyword)
                              .or()
                              .like(StoreProductReply::getNickname, keyword));
        }
        wrapper.orderByDesc(StoreProductReply::getId);

        Page<StoreProductReply> pageParam = new Page<>(page, limit);
        Page<StoreProductReply> resultPage = replyService.page(pageParam, wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", resultPage);
        return result;
    }

    @PostMapping("/set_reply")
    public Map<String, Object> setReply(@RequestBody StoreProductReply reply) {
        if (reply.getId() != null && reply.getMerchantReplyContent() != null) {
            reply.setIsReply(1);
            reply.setMerchantReplyTime((int) (System.currentTimeMillis() / 1000));
            replyService.updateById(reply);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "回复成功");
        return result;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> params) {
        Object idObj = params.get("id");
        if (idObj != null) {
            Integer id = idObj instanceof Integer ? (Integer) idObj : Integer.parseInt(idObj.toString());
            StoreProductReply reply = new StoreProductReply();
            reply.setId(id);
            reply.setIsDel(1);
            replyService.updateById(reply);
        }
        Object idsObj = params.get("ids");
        if (idsObj instanceof List) {
            List<?> idsList = (List<?>) idsObj;
            List<Integer> ids = idsList.stream()
                    .map(o -> o instanceof Integer ? (Integer) o : Integer.parseInt(o.toString()))
                    .collect(Collectors.toList());
            for (Integer id : ids) {
                StoreProductReply reply = new StoreProductReply();
                reply.setId(id);
                reply.setIsDel(1);
                replyService.updateById(reply);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "删除成功");
        return result;
    }
}
