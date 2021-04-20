package com.yjh.web.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.web.blog.domain.Comment;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/20 14:33
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 查看博客评论信息
     *
     * @param blogId 博客id
     * @return 评论信息
     * @author yujunhong
     * @date 2021/4/20 15:00
     */
    List<Comment> listCommentByBlogId(Long blogId);

    /**
     * 保存评论
     *
     * @param comment 评论实体对象
     * @author yujunhong
     * @date 2021/4/20 15:20
     */
    void saveComment(Comment comment);
}
