package com.example.animal.config.security;

import com.example.animal.jwt.JwtAuthenticationTokenFilter;
import com.example.animal.jwt.JwtTokenUtil;
import com.example.animal.security.RestAuthenticationEntryPoint;
import com.example.animal.security.RestfulAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Security 配置类，将所有要用到的组件统一配置到这里，以免出现循环依赖等问题。
 */
@Configuration
public class CommonSecurityConfig {

    // 注册白名单 Bean（稍后就会说）
    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig(){
        return  new IgnoreUrlsConfig();
    }

    // 注册 JWT 工具类 Bean
    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    }

    // 注册自定义认证失败逻辑 Bean
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }

    // 注册自定义权限不足处理 Bean
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler();
    }

    // 注册 JWT 登录授权过滤器 Bean
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
}
