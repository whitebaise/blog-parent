package com.atwwt.blog.service.impl;

import com.atwwt.blog.dao.mapper.CommentMapper;
import com.atwwt.blog.dao.pojo.Comment;
import com.atwwt.blog.dao.pojo.CommentParam;
import com.atwwt.blog.dao.pojo.SysUser;
import com.atwwt.blog.service.CommentsService;
import com.atwwt.blog.service.SysUserService;
import com.atwwt.blog.utils.UserThreadLocal;
import com.atwwt.blog.vo.CommentVo;
import com.atwwt.blog.vo.Result;
import com.atwwt.blog.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        queryWrapper.orderByDesc(Comment::getCreateDate);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return Result.success(copyList(comments));
    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }

    public List<CommentVo> copyList(List<Comment> commentList){
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

//    private CommentVo copy(Comment comment) {
//        CommentVo commentVo = new CommentVo();
//        BeanUtils.copyProperties(comment,commentVo);
//        //作者信息
//        Long authorId = comment.getAuthorId();
//        UserVo userVo = this.sysUserService.findUserVoById(authorId);
//        commentVo.setAuthor(userVo);
//        //子评论
//        Integer level = comment.getLevel();
//        if (1==level){
//            Integer id = comment.getLevel();
//            List<CommentVo> commentVoList=findCommentsByParentId(id);
//            commentVo.setChildrens(commentVoList);
//        }
//        if (level>1){
//            Long toUid = comment.getToUid();
//            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
//            commentVo.setToUser(toUserVo);
//        }
//        return commentVo;
//    }
//
//    private List<CommentVo> findCommentsByParentId(Integer id) {
//        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Comment::getParentId,id);
//        queryWrapper.eq(Comment::getLevel,2);
//        return copyList(commentMapper.selectList(queryWrapper));
//    }
private CommentVo copy(Comment comment) {
    CommentVo commentVo = new CommentVo();
    BeanUtils.copyProperties(comment,commentVo);

    commentVo.setId(comment.getId());
    //时间格式化
    commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    //作者信息
    Long authorId = comment.getAuthorId();
    UserVo userVo = this.sysUserService.findUserVoById(authorId);
    commentVo.setAuthor(userVo);
    //子评论
    Integer level = comment.getLevel();
    if (1 == level){
        Long id = comment.getId();
        List<CommentVo> commentVoList = findCommentsByParentId(id);
        commentVo.setChildrens(commentVoList);
    }
    //to User 给谁评论
    if (level > 1){
        Long toUid = comment.getToUid();
        UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
        commentVo.setToUser(toUserVo);
    }


    return commentVo;
}

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentMapper.selectList(queryWrapper));
    }
}
