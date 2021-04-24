package com.yjh.web.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.web.blog.domain.BlogTags;
import com.yjh.web.blog.domain.Tag;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/23 11:57
 */
public interface BlogTagMapper extends BaseMapper<BlogTags> {
    /**
     * 根据博客主键id获取标签
     *
     * @param blogId 博客主键id
     * @return 博客的标签类型
     * @author 余俊宏
     * @date 2021/4/24
     */
    List<Tag> listTags(Long blogId);
}
