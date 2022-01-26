package com.atwwt.blog.service;

import com.atwwt.blog.dao.pojo.CommentParam;
import com.atwwt.blog.vo.Result;

public interface CommentsService {
    //根据文章id查询所有的评论列表
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
