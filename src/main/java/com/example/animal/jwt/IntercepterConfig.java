package com.example.animal.jwt;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置token拦截器
 *
 * @author 代码的路
 * @date 2022/7/29
 */
@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    //构造方法
    public IntercepterConfig(TokenInterceptor tokenInterceptor){
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/login/**"); // 登录页面不进行拦截
        excludePath.add("/register/**");//注册
        excludePath.add("/logout/**"); // 登出

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);

        WebMvcConfigurer.super.addInterceptors(registry);

    }

    /**
     * 跨域支持
     *
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(false)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600 * 24);
    }


}

