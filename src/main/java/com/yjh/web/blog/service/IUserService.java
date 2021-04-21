package com.yjh.web.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.web.blog.domain.User;

/**
 * @author yujunhong
 * @date 2021/4/21 14:12
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录校验
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户基本信息
     * @author yujunhong
     * @date 2021/4/21 14:14
     */
    User checkUser(String username, String password);
}
