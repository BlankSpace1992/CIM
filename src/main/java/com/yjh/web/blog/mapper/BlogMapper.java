package com.yjh.web.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.vo.BlogQuery;
import com.yjh.web.blog.domain.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/20 09:56
 */
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 查询博客列表
     *
     * @param blog 搜索条件
     * @return 博客列表
     * @author yujunhong
     * @date 2021/4/20 11:01
     */
    List<Blog> listBlogs(BlogQuery blog);


    /**
     * 根据标签查询博客列表
     *
     * @param tagsId 标签id
     * @return 博客列表
     * @author yujunhong
     * @date 2021/4/20 11:20
     */
    List<Blog> listBlogsByTagId(@Param(value = "tagsId") Long tagsId);

    /**
     * 根据主键id获取博客信息
     *
     * @param id 主键id
     * @return 博客信息
     * @author yujunhong
     * @date 2021/4/23 17:27
     */
    Blog getBlogById(@Param(value = "id") Long id);
}
