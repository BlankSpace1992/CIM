package com.yjh.web.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.web.blog.domain.LoginUser;

/**
 * @author yujunhong
 * @date 2021/4/28 16:51
 */
public interface ILoginUserService extends IService<LoginUser> {
    /**
     * 用户登录校验-博客界面
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户基本信息
     * @author yujunhong
     * @date 2021/4/21 14:14
     */
    LoginUser checkLoginUser(String username, String password);
}
