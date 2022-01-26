package com.atwwt.blog.controller;

import com.atwwt.blog.common.aop.LogAnnotation;
import com.atwwt.blog.service.ArticleService;
import com.atwwt.blog.vo.Result;
import com.atwwt.blog.vo.params.ArticleParam;
import com.atwwt.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据进行交互
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping
    @LogAnnotation(module="文章",operation="获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }
    //最热文章
    @PostMapping("hot")
    public Result hotArticle(){
        int limit=5;
        return articleService.hotArticle(limit);
    }
    //最新文章
    @PostMapping("new")
    public Result newArticles(){
        int limit=5;
        return articleService.newArticles(limit);
    }
    @PostMapping("listArchives")
    public Result listArchives(){
        int limit=5;
        return articleService.listArchives();
    }

    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
