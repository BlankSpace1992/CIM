package com.yjh.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yujunhong
 * @date 2021/4/21 15:33
 */
@Configuration
@MapperScan({"com.yjh.web.blog.mapper","com.yjh.web.just_auth.mapper"})
public class ApplicationConfig {
}
