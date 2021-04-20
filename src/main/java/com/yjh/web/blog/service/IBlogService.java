package com.yjh.web.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.vo.BlogQuery;
import com.yjh.web.blog.domain.Blog;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/20 09:54
 */
public interface IBlogService extends IService<Blog> {
    /**
     * 保存博客
     *
     * @param blog 博客实体对象
     * @author yujunhong
     * @date 2021/4/20 11:41
     */
    void saveBlog(Blog blog);

    /**
     * 更新博客
     *
     * @param blog 博客实体对象
     * @author yujunhong
     * @date 2021/4/20 11:41
     */
    void updateBlog(Blog blog);

    /**
     * 删除博客
     *
     * @param id 博客主键id
     * @author yujunhong
     * @date 2021/4/20 11:41
     */
    void deleteBlog(Long id);

    /**
     * 根据id 获取博客数据
     *
     * @param id 主键id
     * @return 博客实体对象
     * @author yujunhong
     * @date 2021/4/20 10:07
     */
    Blog getBlog(Long id);

    /**
     * 获取博客信息并将信息转换为markdown 并且浏览数加1
     *
     * @param id 主键id
     * @return 博客实体对象
     * @author yujunhong
     * @date 2021/4/20 10:49
     */
    Blog getAndConvert(Long id);

    /**
     * 查询博客列表
     *
     * @param pageable 分页数据
     * @param blog     搜索条件
     * @return 博客列表
     * @author yujunhong
     * @date 2021/4/20 11:01
     */
    List<Blog> listBlogs(Pageable pageable, BlogQuery blog);

    /**
     * 根据标签查询博客列表
     *
     * @param pageable 分页数据
     * @param tagsId   标签id
     * @return 博客列表
     * @author yujunhong
     * @date 2021/4/20 11:20
     */
    List<Blog> listBlogsByTagId(Pageable pageable, Long tagsId);

    /**
     * 获取最新推荐
     *
     * @param size 显示最新推荐数量
     * @param blog 搜索条件
     * @return 博客列表
     * @author yujunhong
     * @date 2021/4/20 11:36
     */
    List<Blog> listBlogsByRecommend(Integer size, BlogQuery blog);


}
