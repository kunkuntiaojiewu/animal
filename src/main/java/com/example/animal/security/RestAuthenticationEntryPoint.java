package com.example.animal.security;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 自定义认证失败处理：没有登录或 token 过期时
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当认证失败时，此方法会被调用
     * @param request 请求对象
     * @param response 响应对象
     * @param authException 认证失败时抛出的异常
     * @throws IOException IO 异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 设置响应头，允许任何域进行跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 设置响应头，指示响应不应被缓存
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        // 设置响应的字符编码为 UTF-8
        response.setCharacterEncoding("UTF-8");
        // 设置响应内容类型为 JSON
        response.setContentType("application/json");
        // 构造JSON响应体
        String jsonResponse = "{\"status\": \"error\", \"message\": \"" + authException.getMessage() + "\"}";
        // 将认证失败的消息写入响应体
        response.getWriter().println(JSONUtil.parse(jsonResponse));
        // 刷新响应流，确保数据被发送
        response.getWriter().flush();
    }
}
