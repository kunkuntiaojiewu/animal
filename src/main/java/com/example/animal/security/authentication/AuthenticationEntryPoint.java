package com.example.animal.security.authentication;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationEntryPoint {
    /**
     * 处理认证失败的情况
     * @param request      表示客户端请求的信息
     * @param response     表示服务器对客户端请求的响应
     * @param authException 表示身份验证过程中发生的异常
     * @throws IOException      IO 异常
     * @throws ServletException Servlet 异常
     */
    void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException;
}
