package com.yjh.web.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.web.blog.domain.Tag;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/20 16:48
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 获取标签列表详情--不分页
     *
     * @param blogId 博客主键id
     * @return tag列表
     * @author yujunhong
     * @date 2021/4/20 17:28
     */
    List<Tag> listTags(Long blogId);
}
