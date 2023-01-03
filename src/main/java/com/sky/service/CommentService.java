package com.sky.service;

import com.sky.entity.Comment;

import java.util.List;

/**
 * @Description: 博客评论业务层接口

 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    int saveComment(Comment comment);

    //删除评论
    void deleteComment(Comment comment,Long id);

}