package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yjh.NotFoundException;
import com.yjh.common.constants.Constants;
import com.yjh.util.MarkdownUtils;
import com.yjh.vo.BlogQuery;
import com.yjh.web.blog.domain.Blog;
import com.yjh.web.blog.domain.BlogTags;
import com.yjh.web.blog.mapper.BlogMapper;
import com.yjh.web.blog.service.BlogTagService;
import com.yjh.web.blog.service.IBlogService;
import com.yjh.web.blog.service.ITagService;
import com.yjh.web.blog.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private BlogTagService blogTagService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        // 保存userId
        blog.setUserId(blog.getUser().getId());
        // 设置分类
        blog.setTypeId(blog.getType().getId());
        this.save(blog);
        // 保存标签
        List<BlogTags> blogTagsList = new ArrayList<>();
        String tagIds = blog.getTagIds().replace("[", "").replace("]", "");
        String[] tagIdsArray = tagIds.split(",");
        for (String tagId : tagIdsArray) {
            BlogTags blogTags = new BlogTags();
            blogTags.setBlogsId(blog.getId());
            blogTags.setTagsId(Long.valueOf(tagId));
            blogTagsList.add(blogTags);
        }
        blogTagService.saveBatch(blogTagsList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBlog(Blog blog) {
        // 保存userId
        blog.setUserId(blog.getUser().getId());
        // 设置分类
        blog.setTypeId(blog.getType().getId());
        blog.setUpdateTime(new Date());
        // 删除原有标签
        LambdaQueryWrapper<BlogTags> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BlogTags::getBlogsId, blog.getId());
        blogTagService.remove(wrapper);
        // 保存标签
        List<BlogTags> blogTagsList = new ArrayList<>();
        String tagIds = blog.getTagIds().replace("[", "").replace("]", "");
        String[] tagIdsArray = tagIds.split(",");
        for (String tagId : tagIdsArray) {
            BlogTags blogTags = new BlogTags();
            blogTags.setBlogsId(blog.getId());
            blogTags.setTagsId(Long.valueOf(tagId));
            blogTagsList.add(blogTags);
        }
        blogTagService.saveBatch(blogTagsList);
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
        return Optional.ofNullable(baseMapper.getBlogById(id)).orElseThrow(() -> new NotFoundException("该博客不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Blog getAndConvert(Long id) {
        // 查询博客信息
        Blog blog = Optional.ofNullable(baseMapper.getBlogById(id)).orElseThrow(() -> new NotFoundException("该博客不存在"));
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
