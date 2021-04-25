package com.yjh.config;

import com.yjh.interceptor.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yujunhong
 * @date 2021/4/25 16:20
 */
@Configuration
public class AccessLimitConfig implements WebMvcConfigurer {
    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器到SpringBoot
        registry.addInterceptor(accessLimitInterceptor);
    }
}
