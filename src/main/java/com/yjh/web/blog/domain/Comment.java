package com.yjh.web.blog.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论主表
 *
 * @author yujunhong
 * @date 2021/04/20 02:31:57
 */
@ApiModel(value = "评论主表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_comment")
public class Comment {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @Excel(name = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 是否用户评论
     */
    @ApiModelProperty(value = "是否用户评论")
    @Excel(name = "是否用户评论")
    private String adminComment;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @Excel(name = "头像")
    private String avatar;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @Excel(name = "内容")
    private String content;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Excel(name = "创建时间")
    private Date createTime;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Excel(name = "邮箱")
    private String email;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @Excel(name = "昵称")
    private String nickname;
    /**
     * 博客主键id
     */
    @ApiModelProperty(value = "博客主键id")
    @Excel(name = "博客主键id")
    private Long blogId;
    /**
     * 父节点评论id
     */
    @ApiModelProperty(value = "父节点评论id")
    @Excel(name = "父节点评论id")
    private Long parentCommentId;
    /**
     * 删除标志 0 未删除 1删除
     */
    @ApiModelProperty(value = "删除标志 0 未删除 1删除")
    @Excel(name = "删除标志 0 未删除 1删除")
    private String delFlag;

    /**
     * 博客实体对象
     */
    @ApiModelProperty(value = "博客实体对象")
    @Excel(name = "博客实体对象")
    @TableField(exist = false)
    private Blog blog;

    /**
     * 回复评论
     */
    @ApiModelProperty(value = "回复评论")
    @Excel(name = "回复评论")
    @TableField(exist = false)
    private List<Comment> replyComments =new ArrayList<>();

    /**
     * 上一个回复的评论
     */
    @ApiModelProperty(value = "上一个回复的评论")
    @Excel(name = "上一个回复的评论")
    @TableField(exist = false)
    private String parentCommentName;
}
