package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.web.blog.domain.BlogTags;
import com.yjh.web.blog.mapper.BlogTagMapper;
import com.yjh.web.blog.service.BlogTagService;
import org.springframework.stereotype.Service;

/**
 * @author yujunhong
 * @date 2021/4/23 11:58
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTags> implements BlogTagService {
}
