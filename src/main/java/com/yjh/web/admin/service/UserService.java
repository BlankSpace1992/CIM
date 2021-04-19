package com.yjh.web.admin.service;

import com.yjh.web.admin.domain.po.User;

/**
 * Created by limi on 2017/10/15.
 */
public interface UserService {

    User checkUser(String username, String password);
}
