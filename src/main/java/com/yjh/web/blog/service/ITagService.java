package com.yjh.web.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.web.blog.domain.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/20 16:47
 */
public interface ITagService extends IService<Tag> {

    /**
     * 保存标签
     *
     * @param tag 标签实体对象
     * @author yujunhong
     * @date 2021/4/20 17:24
     */
    void saveTag(Tag tag);

    /**
     * 获取tag详情
     *
     * @param id tag主键id
     * @author yujunhong
     * @date 2021/4/20 17:24
     */
    void getTag(Long id);

    /**
     * 通过名称获取标签
     *
     * @param name 标签名称
     * @author yujunhong
     * @date 2021/4/20 17:24
     */
    void getTagByName(String name);

    /** 获取标签列表详情
     * @param pageable 分页详情
     * @return tag列表
     * @author yujunhong
     * @date 2021/4/20 17:28
     */
    List<Tag> listTags(Pageable pageable);
}
