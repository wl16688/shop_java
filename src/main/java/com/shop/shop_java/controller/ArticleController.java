package com.shop.shop_java.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.shop_java.common.Result;
import com.shop.shop_java.entity.Article;
import com.shop.shop_java.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<Page<Article>> list(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "15") Integer limit) {
        Page<Article> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getId);
        return Result.success(articleService.page(pageParam, wrapper));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Article article) {
        if (article.getAddTime() == null) {
            article.setAddTime(String.valueOf(System.currentTimeMillis() / 1000));
        }
        return Result.success(articleService.saveOrUpdate(article));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(articleService.removeById(id));
    }
}
