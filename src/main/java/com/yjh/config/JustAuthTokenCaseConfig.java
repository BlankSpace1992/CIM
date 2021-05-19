package com.yjh.config;

import com.alibaba.fastjson.JSON;
import com.yjh.util.StringUtils;
import me.zhyd.oauth.model.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author yujunhong
 * @date 2021/5/19 09:40
 */
@Configuration
public class JustAuthTokenCaseConfig {
    @SuppressWarnings(value = "rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    private BoundHashOperations<String, String, AuthToken> valueOperations;

    /**
     * 初始化
     *
     * @author yujunhong
     * @date 2021/5/19 9:42
     */
    @SuppressWarnings(value = "unchecked")
    @PostConstruct
    public void init() {
        valueOperations = redisTemplate.boundHashOps("JUSTAUTH::TOKEN");
    }

    /**
     * 保存token
     *
     * @param uuid      用户第三方id
     * @param authToken 用户token值
     * @return 用户token值
     * @author yujunhong
     * @date 2021/5/19 9:42
     */
    public AuthToken saveOrUpdate(String uuid, AuthToken authToken) {
        valueOperations.put(uuid, authToken);
        return authToken;
    }

    /**
     * 根据用户uuid查询token
     *
     * @param uuid 用户第三方id
     * @return 对应用户的token值
     * @author yujunhong
     * @date 2021/5/19 9:47
     */
    public AuthToken getByUuid(String uuid) {
        Object token = valueOperations.get(uuid);
        if (StringUtils.isNull(token)) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(token), AuthToken.class);
    }

    /**
     * 查询所有的token
     *
     * @return 获取所有的token
     * @author yujunhong
     * @date 2021/5/19 9:51
     */
    public List<AuthToken> listAllTokens() {
        return new LinkedList<>(Objects.requireNonNull(valueOperations.values()));
    }

    /**
     * 根据uuid移除token
     *
     * @param uuid 用户第三方id
     * @author yujunhong
     * @date 2021/5/19 9:53
     */
    public void remove(String uuid) {
        valueOperations.rename(uuid);
    }
}
