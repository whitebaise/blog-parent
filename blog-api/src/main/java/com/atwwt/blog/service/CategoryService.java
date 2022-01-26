package com.atwwt.blog.service;

import com.atwwt.blog.vo.CategoryVo;
import com.atwwt.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
