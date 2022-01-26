package com.atwwt.blog.service;

import com.atwwt.blog.vo.Result;
import com.atwwt.blog.vo.TagVo;

import java.util.List;

public interface TagService {
        List<TagVo> findTagsByArticleId(Long articleId);

        Result hots(int limit);
    //查询所有的文章标签
      Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
