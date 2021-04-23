package com.yjh.web.blog.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yujunhong
 * @date 2021/04/23 11:56:46
 */
@ApiModel(value = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_blog_tags")
public class BlogTags {
    /**
     * 博客主键id
     */
    @ApiModelProperty(value = "博客主键id")
    @Excel(name = "博客主键id")
    private Long blogsId;
    /**
     * 标签主键id
     */
    @ApiModelProperty(value = "标签主键id")
    @Excel(name = "标签主键id")
    private Long tagsId;
}
