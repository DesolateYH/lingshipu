package com.example.demo;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: lingshipu
 * @description: 拦截器
 * @author: QWS
 * @create: 2020-03-07 01:43
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    CheckParamsInterceptor checkSourceInterceptor = new CheckParamsInterceptor();
    CheckAdminInterceptor checkAdminInterceptor = new CheckAdminInterceptor();
    //增加校验拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 这个地方可以定义拦截器的具体的路径
        registry.addInterceptor(checkSourceInterceptor).addPathPatterns("/**");
        registry.addInterceptor(checkAdminInterceptor).addPathPatterns("/**");
    }

//    /**
//     * 注册拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //addPathPattern后跟拦截地址，excludePathPatterns后跟排除拦截地址
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
//                //登录页面
//                .excludePathPatterns("/admin/adminLogin")
//                //注册页面
//                .excludePathPatterns("/admin/adminLoginError")
//                .excludePathPatterns("/user/getItem")
//                .excludePathPatterns("/user/getCode");
//
//          /*      //注册方法
//                .excludePathPatterns("/register.json");*/
//    }
//
//    class MyInterceptor implements HandlerInterceptor {
//        /**
//         * 在请求处理之前进行调用（Controller方法调用之前
//         */
//        @Override
//        public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//            HttpSession session = httpServletRequest.getSession();
//            //获取登录的session信息
//            Object user =  session.getAttribute("user_id");
//            if (user != null) {
//                return true;
//            } else {
//                //未登录自动跳转界面
//                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/admin/adminLoginError");
//                return false;
//            }
//        }
//
//        /**
//         * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
//         */
//        @Override
//        public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
//
//            System.out.println("postHandle被调用\n");
//        }
//
//        /**
//         * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
//         */
//        @Override
//        public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//            System.out.println("afterCompletion被调用\n");
//        }
//    }
}

