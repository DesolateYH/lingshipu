package com.example.demo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @program: lingshipu
 * @description: 拦截器
 * @author: QWS
 * @create: 2020-03-06 00:03
 */
public class Filter implements javax.servlet.Filter {
    private final List<String> allowedOrigins = Arrays.asList("http://218.74.47.78","http://localhost:10001", "http://127.0.0.1","http://chinaqwe.top","http://lsp.chinaqwe.top");//"00允许跨域的地址

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----Filter初始化----");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;


        System.out.println("----调用service之前执行一段代码----");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Accept, Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
        // 是否允许浏览器携带用户身份信息（cookie）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 图片上传会用到
        if ("OPTIONS".equals(request.getMethod())) {
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("----Filter销毁----");
    }
}
