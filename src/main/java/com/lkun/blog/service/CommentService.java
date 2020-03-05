package com.lkun.blog.service;

import com.lkun.blog.po.Comment;

import java.util.List;

/**
 * @author LK
 * @date 2019/9/9 15:18:59
 * @description
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
