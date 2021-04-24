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
import java.util.*;
import java.util.stream.Collectors;

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
        wrapper.orderByAsc(Comment::getId);
        List<Comment> comments = this.list(wrapper);
        // 根据父节点id进行分组
        Map<Long, List<Comment>> listMap = comments.stream().collect(Collectors.groupingBy(Comment::getParentCommentId));
        // 只留下getParentCommentId==-1的评论
        List<Comment> commentList = comments.stream().filter(comment -> comment.getParentCommentId() == -1).collect(Collectors.toList());
        return getCommentTree(commentList, listMap);
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
     * 构造评论数
     *
     * @param comments 全部评论集合
     * @param listMap  以父评论id作为key的评论集合
     * @author 余俊宏
     * @date 2021/4/24
     */
    private List<Comment> getCommentTree(List<Comment> comments, Map<Long, List<Comment>> listMap) {
        // ParentCommentId 为-1的评论进行遍历
        for (Comment comment : comments) {
            List<Comment> replyComments = comment.getReplyComments();
            // 查找到该评论下的回复评论
            if (listMap.containsKey(comment.getId())) {
                // 统一放入同一个回复评论中
                replyComments.addAll(listMap.get(comment.getId()));
                // 循环遍历,查找回复评论下的回复评论
                buildReplyTree(listMap.get(comment.getId()), replyComments, listMap, comment);
            }
        }
        return comments;
    }

    /**
     * 循环遍历,查找回复评论下的回复评论
     *
     * @param comments      子评论集合
     * @param replyComments 获取评论集合
     * @param listMap       以父评论id作为key的评论集合
     * @param parentComment 父评论集合
     * @author 余俊宏
     * @date 2021/4/24
     */
    private void buildReplyTree(List<Comment> comments, List<Comment> replyComments, Map<Long, List<Comment>> listMap, Comment parentComment) {
        for (Comment comment : comments) {
            comment.setParentCommentName(parentComment.getNickname());
            if (listMap.containsKey(comment.getId())) {
                replyComments.addAll(listMap.get(comment.getId()));
                buildReplyTree(listMap.get(comment.getId()), replyComments, listMap, comment);
            }
        }
    }
}
