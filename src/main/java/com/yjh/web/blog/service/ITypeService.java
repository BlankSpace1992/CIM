package com.yjh.web.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.web.blog.domain.Type;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yujunhong
 * @date 2021/4/21 13:45
 */
public interface ITypeService extends IService<Type> {

    /**
     * 保存分类
     *
     * @param type 分类实体对象
     * @author yujunhong
     * @date 2021/4/21 13:48
     */
    void saveType(Type type);

    /**
     * 根据主键id 获取分类实体
     *
     * @param id 主键id
     * @return 分类实体对象
     * @author yujunhong
     * @date 2021/4/21 13:49
     */
    Type getType(Long id);

    /**
     * 通过名称获取的实体对象
     *
     * @param name 分类名称
     * @return 分类实体对象
     * @author yujunhong
     * @date 2021/4/21 13:50
     */
    Type getTypeByName(String name);

    /**
     * 分类详情 分类查询
     *
     * @param pageable 分页属性
     * @return 分类实体集合
     * @author yujunhong
     * @date 2021/4/21 14:04
     */
    List<Type> listTypes(Pageable pageable);

    /**
     * 分类详情
     *
     * @return 分类实体集合
     * @author yujunhong
     * @date 2021/4/21 14:04
     */
    List<Type> listTypes();

    /**
     * 更新分类
     *
     * @param type 分类实体对象
     * @author yujunhong
     * @date 2021/4/21 14:05
     */
    void updateType(Type type);

    /**
     * 删除分类
     *
     * @param id 分类主键id
     * @author yujunhong
     * @date 2021/4/21 14:06
     */
    void deleteType(Long id);
}
