package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.common.constants.Constants;
import com.yjh.util.MD5Utils;
import com.yjh.web.blog.domain.LoginUser;
import com.yjh.web.blog.mapper.LoginUserMapper;
import com.yjh.web.blog.service.ILoginUserService;
import org.springframework.stereotype.Service;

/**
 * @author yujunhong
 * @date 2021/4/28 16:52
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements ILoginUserService {
    @Override
    public LoginUser checkLoginUser(String username, String password) {
        LambdaQueryWrapper<LoginUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoginUser::getUsername, username);
        wrapper.eq(LoginUser::getPassword, MD5Utils.code(password));
        wrapper.eq(LoginUser::getDelFlag, Constants.DELETE_EXIST);
        return this.getOne(wrapper);
    }
}
