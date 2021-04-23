package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.yjh.NotFoundException;
import com.yjh.vo.BlogQuery;
import com.yjh.web.blog.domain.Blog;
import com.yjh.web.blog.domain.Type;
import com.yjh.web.blog.mapper.TypeMapper;
import com.yjh.web.blog.service.IBlogService;
import com.yjh.web.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/21 13:46
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {
    @Autowired
    private IBlogService blogService;

    @Override
    public void saveType(Type type) {
        // 查询当前分类名称是否存在
        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Type::getName, type.getName());
        if (this.getOne(wrapper) != null) {
            throw new NotFoundException("分类名称已经存在,不允许再添加");
        }
        this.save(type);
    }

    @Override
    public Type getType(Long id) {
        return this.getById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Type::getName, name);
        return this.getOne(wrapper);
    }

    @Override
    public List<Type> listTypes(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        return this.list();
    }

    @Override
    public List<Type> listTypes() {
        List<Type> list = this.list();
        list.forEach(item -> {
            List<Blog> blogs = blogService.listBlogs(BlogQuery.builder().typeId(item.getId()).build());
            item.setBlogs(blogs);
        });
        return list;
    }

    @Override
    public void updateType(Type type) {
        // 查询当前分类是否存在
        Type typeOld = this.getById(type.getId());
        if (typeOld == null) {
            throw new NotFoundException("分类不存在,不允许更新");
        }
        if (type.getName().equals(typeOld.getName())) {
            throw new NotFoundException("分类名称已经存在,不允许更新");
        }
        this.updateById(type);
    }

    @Override
    public void deleteType(Long id) {
        this.removeById(id);
    }
}
