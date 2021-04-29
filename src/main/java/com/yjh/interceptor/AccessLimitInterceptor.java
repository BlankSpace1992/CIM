package com.yjh.interceptor;

import com.yjh.common.anotation.AccessLimit;
import com.yjh.util.RedisUtil;
import com.yjh.util.StringUtils;
import com.yjh.web.blog.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yujunhong
 * @date 2021/4/25 09:49
 */
@Component
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisUtil redisUtil;


    @Override


    public boolean
    preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断请求是否是方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod methodHandle = (HandlerMethod) handler;
            // 获取方法中是否有AccessLimit注解
            AccessLimit accessLimit = methodHandle.getMethodAnnotation(AccessLimit.class);
            // 如果注解不存在,则返回true 不做任何处理
            if (StringUtils.isNull(accessLimit)) {
                return true;
            }
            // 获取注解中秒数
            int seconds = accessLimit.seconds();
            // 获取注解中最多访问次数
            int maxCount = accessLimit.maxCount();
            // 是否需要登录
            boolean needLogin = accessLimit.needLogin();
            // 获取URI
            String requestUri = request.getRequestURI();
            // 如果需要登录
            if (needLogin) {
                // 获取session
                HttpSession session = request.getSession();
                // 获取用户信息
                User user = (User) session.getAttribute("user");
                if (StringUtils.isNull(user)) {
                    return false;
                }
                requestUri += user.getId();
            }
            // 从redis中获取已经访问次数
            Object value = redisUtil.get(requestUri);
            int count = value == null ? 0 : (Integer) value;
            // 第一次访问
            if (StringUtils.isNull(value)) {
                redisUtil.set(requestUri, 1, seconds);
            }
            // 访问次数还未达到最大值
            else if (count < maxCount) {
                redisUtil.increment(requestUri, 1L);
            }
            // 超出访问次数
            else {
                throw new RuntimeException("超出访问次数");
            }
        }
        return true;
    }
}
