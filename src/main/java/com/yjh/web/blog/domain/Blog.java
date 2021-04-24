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
 * 博客实体对象
 *
 * @author yujunhong
 * @date 2021/04/20 10:31:56
 */
@ApiModel(value = "博客实体对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_blog")
public class Blog {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @Excel(name = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 是否赞赏
     */
    @ApiModelProperty(value = "是否赞赏")
    @Excel(name = "是否赞赏")
    private String appreciation;
    /**
     * 是否评论
     */
    @ApiModelProperty(value = "是否评论")
    @Excel(name = "是否评论")
    private String commentabled;
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
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Excel(name = "描述")
    private String description;
    /**
     * 第一张图
     */
    @ApiModelProperty(value = "第一张图")
    @Excel(name = "第一张图")
    private String firstPicture;
    /**
     * 标志
     */
    @ApiModelProperty(value = "标志")
    @Excel(name = "标志")
    private String flag;
    /**
     * 是否分享评论
     */
    @ApiModelProperty(value = "是否分享评论")
    @Excel(name = "是否分享评论")
    private String shareStatement;
    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐")
    @Excel(name = "是否推荐")
    private String recommend;
    /**
     * 是否发布
     */
    @ApiModelProperty(value = "是否发布")
    @Excel(name = "是否发布")
    private String published;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @Excel(name = "标题")
    private String title;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @Excel(name = "更新时间")
    private Date updateTime;
    /**
     * 查看数量
     */
    @ApiModelProperty(value = "查看数量")
    @Excel(name = "查看数量")
    private Integer views;
    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    @Excel(name = "分类ID")
    private Long typeId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 删除标志 0 未删除 1删除
     */
    @ApiModelProperty(value = "删除标志 0 未删除 1删除")
    @Excel(name = "删除标志 0 未删除 1删除")
    private String delFlag;
    /**
     * 用户实体对象
     */
    @ApiModelProperty(value = "用户实体对象")
    @TableField(exist = false)
    private User user;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称")
    @TableField(exist = false)
    private Type type;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    @TableField(exist = false)
    private List<Tag> tags;

    /**
     * 标签id集合
     */
    @ApiModelProperty(value = "标签id集合")
    @TableField(exist = false)
    private String tagIds;

    /**
     * @author yujunhong
     * @date 2021/4/23 10:54
     */
    public void getTagList() {
        List<String> tagIdsList = new ArrayList<>();
        this.getTags().forEach(tag -> {
            tagIdsList.add(String.valueOf(tag.getId()));
        });
        this.tagIds = tagIdsList.toString().replace("[", "").replace("]", "");
    }
}
