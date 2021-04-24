package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.common.constants.Constants;
import com.yjh.util.PushWechatMessageUtil;
import com.yjh.web.blog.domain.Comment;
import com.yjh.web.blog.mapper.CommentMapper;
import com.yjh.web.blog.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        List<Comment> comments = this.list(wrapper);
        return eachComment(comments);
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

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
