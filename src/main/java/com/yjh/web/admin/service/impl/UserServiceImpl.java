package com.yjh.web.admin.service.impl;

import com.yjh.dao.UserRepository;
import com.yjh.web.admin.domain.po.User;
import com.yjh.util.MD5Utils;
import com.yjh.web.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by limi on 2017/10/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
