package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.common.constants.Constants;
import com.yjh.util.PushWechatMessageUtil;
import com.yjh.web.blog.domain.Comment;
import com.yjh.web.blog.mapper.CommentMapper;
import com.yjh.web.blog.service.ICommentService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author yujunhong
 * @date 2021/4/20 14:34
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {


    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getDelFlag, Constants.DELETE_EXIST);
        wrapper.eq(Comment::getBlogId, blogId);
        wrapper.orderByDesc(Comment::getParentCommentId);
        return this.list(wrapper);
    }

    @Override
    public void saveComment(Comment comment) {
        // 获取评论完整信息
        String result = PushWechatMessageUtil.pushMessageByPost(Constants.COMMENT_TITTLE, getCommentContent(comment));
        // 拼接头像地址
        comment.setAvatar(Constants.AVATAR_REQUEST_URL_HEAD + (new Random().nextInt(1000) + 1) + Constants.AVATAR_REQUEST_URL_END);
        comment.setCreateTime(new Date());

        comment.setBlogId(comment.getBlog().getId());
        comment.setCreateTime(new Date());
        this.save(comment);
    }

    private String getCommentContent(Comment comment) {
        return "评论博客标题:" + comment.getBlog().getTitle() +
                "<br>用户名称:" + comment.getNickname() +
                "<br>用户邮箱:" + comment.getEmail() +
                "<br>评论内容:" + comment.getContent() +
                "<br>评论时间:" + new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());
    }
}
