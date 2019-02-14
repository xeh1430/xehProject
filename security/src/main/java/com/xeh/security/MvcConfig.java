package com.xeh.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:xeh
 * @Date:2018/11/16 14:33
 * @Description: 消息转换器
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 实现一个请求到视图的映射，无需书写controller
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

}