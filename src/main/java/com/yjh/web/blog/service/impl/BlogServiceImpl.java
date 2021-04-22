package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yjh.NotFoundException;
import com.yjh.common.constants.Constants;
import com.yjh.util.MarkdownUtils;
import com.yjh.vo.BlogQuery;
import com.yjh.web.blog.domain.Blog;
import com.yjh.web.blog.domain.Tag;
import com.yjh.web.blog.domain.User;
import com.yjh.web.blog.mapper.BlogMapper;
import com.yjh.web.blog.service.IBlogService;
import com.yjh.web.blog.service.ITagService;
import com.yjh.web.blog.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author yujunhong
 * @date 2021/4/20 09:55
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Autowired
    private IUserService userService;
    @Autowired
    private ITagService tagService;

    @Override
    public void saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        this.save(blog);
    }

    @Override
    public void updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        this.updateById(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        // TODO: 2021/4/20  需要删除评论目前未删除
        Blog blog = Optional.ofNullable(this.getById(id)).orElseThrow(() -> new NotFoundException("该博客不存在,删除失败"));
        blog.setDelFlag(Constants.DELETE_NOT_EXIST);
        this.updateById(blog);
    }

    @Override
    public Blog getBlog(Long id) {
        // 需要判空--当前博客是否存在
        return Optional.ofNullable(this.getById(id)).orElseThrow(() -> new NotFoundException("该博客不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Blog getAndConvert(Long id) {
        // 查询博客信息
        Blog blog = Optional.ofNullable(this.getById(id)).orElseThrow(() -> new NotFoundException("该博客不存在"));
        // 根据用户id获取用户信息
        User user = userService.getById(blog.getId());
        blog.setUser(user);
        // 获取标签名称
        List<Tag> tags = tagService.listTags(blog.getId());
        blog.setTags(tags);
        // 浏览数加1
        blog.setViews(blog.getViews() + 1);
        this.updateById(blog);

        Blog returnBlog = new Blog();
        BeanUtils.copyProperties(blog, returnBlog);
        // 处理内容 转换为markdown
        String content = returnBlog.getContent();
        returnBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return returnBlog;
    }

    @Override
    public Page<Blog> listBlogs(Pageable pageable, BlogQuery blog) {
        Page<Blog> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Blog> blogs = baseMapper.listBlogs(blog);
        return page;
    }

    @Override
    public List<Blog> listBlogs(BlogQuery blog) {
        return baseMapper.listBlogs(blog);
    }

    @Override
    public List<Blog> listBlogsByTagId(Pageable pageable, Long tagsId) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        return baseMapper.listBlogsByTagId(tagsId);
    }

    @Override
    public List<Blog> listBlogsByTagId(Long tagsId) {
        return baseMapper.listBlogsByTagId(tagsId);
    }

    @Override
    public List<Blog> listBlogsByRecommend(Integer size, BlogQuery blog) {
        PageHelper.startPage(0, size);
        return baseMapper.listBlogs(blog);
    }


}
