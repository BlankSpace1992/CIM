package com.yjh.web.just_auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.config.JustAuthTokenCaseConfig;
import com.yjh.util.StringUtils;
import com.yjh.web.just_auth.domain.JustAuthUser;
import com.yjh.web.just_auth.mapper.JustAuthUserMapper;
import com.yjh.web.just_auth.service.JustAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yujunhong
 * @date 2021/5/19 10:19
 */
@Service
public class JustAuthUserServiceImpl extends ServiceImpl<JustAuthUserMapper, JustAuthUser> implements JustAuthUserService {
    @Autowired
    private JustAuthTokenCaseConfig justAuthTokenCaseConfig;

    @Override
    public void saveOrUpdateAuth(JustAuthUser justAuthUser) {
        justAuthTokenCaseConfig.saveOrUpdate(justAuthUser.getUuid(), justAuthUser.getToken());
        this.saveOrUpdate(justAuthUser);
    }

    @Override
    public JustAuthUser getByUuid(String uuid) {
        JustAuthUser justAuthUser = this.getById(uuid);
        if (StringUtils.isNotNull(justAuthUser)) {
            justAuthUser.setToken(justAuthTokenCaseConfig.getByUuid(uuid));
        }
        return justAuthUser;
    }

    @Override
    public void removeByUuid(String uuid) {
        justAuthTokenCaseConfig.remove(uuid);
        this.removeById(uuid);
    }
}
