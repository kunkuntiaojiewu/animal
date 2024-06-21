package com.example.animal.security;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义返回结果：访问权限不足时
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        // 构造一个简单的JSON对象表示拒绝访问的响应
        // 这里使用Fastjson的JSON.toJSONString方法将Map转换为JSON字符串
        Map<String, Object> result = new HashMap<>();
        result.put("code", 403); // 通常HTTP 403 Forbidden的响应码
        result.put("message", accessDeniedException.getMessage());
        result.put("data", null); // 通常没有数据返回

        response.getWriter().println(JSONUtil.parse(result));
        response.getWriter().flush();
    }
}
