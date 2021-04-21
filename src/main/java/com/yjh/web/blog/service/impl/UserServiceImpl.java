package com.yjh.web.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.common.constants.Constants;
import com.yjh.util.MD5Utils;
import com.yjh.web.blog.domain.User;
import com.yjh.web.blog.mapper.UserMapper;
import com.yjh.web.blog.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author yujunhong
 * @date 2021/4/21 14:13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User checkUser(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        wrapper.eq(User::getPassword, MD5Utils.code(password));
        wrapper.eq(User::getDelFlag, Constants.DELETE_EXIST);
        return this.getOne(wrapper);
    }
}
