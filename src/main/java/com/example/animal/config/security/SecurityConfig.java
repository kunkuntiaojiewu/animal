package com.example.animal.config.security;


import com.example.animal.jwt.JwtAuthenticationTokenFilter;
import com.example.animal.security.RestAuthenticationEntryPoint;
import com.example.animal.security.RestfulAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Spring Security 白名单资源路径配置
    private final IgnoreUrlsConfig ignoreUrlsConfig;

    // 自定义返回结果：没有权限访问时
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    // 自定义返回结果：没有登录或 token 过期时
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    // JWT 拦截器
    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 构造函数注入
    public SecurityConfig(IgnoreUrlsConfig ignoreUrlsConfig, RestfulAccessDeniedHandler restfulAccessDeniedHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
        this.ignoreUrlsConfig = ignoreUrlsConfig;
        this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    /**
     * 配置 Spring Security 的过滤链
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 配置 URL 的授权规则
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // 遍历白名单，将白名单中的 URL 配置为完全公开，不需要任何权限
                        .requestMatchers(ignoreUrlsConfig.getUrls().toArray(new String[0])).permitAll()
                        // 允许所有 OPTIONS 请求（通常用于 CORS 预检请求）
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        // 配置其他所有请求都需要认证
                        .anyRequest().authenticated()
                )
                // 禁用 CSRF 保护（在使用 token 时通常不需要）
                .csrf(AbstractHttpConfigurer::disable)
                // 配置 session 策略为无状态，即不创建 session
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置异常处理器
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        // 当访问被拒绝时使用自定义的处理器返回响应
                        .accessDeniedHandler(restfulAccessDeniedHandler)
                        // 当未认证或 token 过期时使用自定义的处理器返回响应
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                )
                // 在 UsernamePasswordAuthenticationFilter 之前添加 JWT 拦截器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
