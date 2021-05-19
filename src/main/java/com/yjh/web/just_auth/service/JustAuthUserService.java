package com.yjh.web.just_auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.web.just_auth.domain.JustAuthUser;

/**
 * @author yujunhong
 * @date 2021/5/19 10:19
 */
public interface JustAuthUserService extends IService<JustAuthUser> {

    /**
     * 新增或保存授权用户
     *
     * @param justAuthUser 第三方权限实体
     * @author yujunhong
     * @date 2021/5/19 10:22
     */
    void saveOrUpdateAuth(JustAuthUser justAuthUser);

    /**
     * 根据用户uuid 查询token
     *
     * @param uuid 用户第三方id
     * @return 第三方权限实体
     * @author yujunhong
     * @date 2021/5/19 10:29
     */
    JustAuthUser getByUuid(String uuid);

    /**
     * 根据用户uuid移除权限
     *
     * @param uuid 用户第三方id
     * @author yujunhong
     * @date 2021/5/19 10:30
     */
    void removeByUuid(String uuid);

}
