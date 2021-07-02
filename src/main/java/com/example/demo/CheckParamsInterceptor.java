package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: lingshipu
 * @description: 参数检查注解拦截器
 * @author: QWS
 * @create: 2021-02-19 20:38
 */
public class CheckParamsInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CheckParamsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            logger.warn("UnSupport handler");
            return true;
        }
        List<String> list = getParamsName((HandlerMethod) handler);
        System.out.println(list);
        for (String s : list) {
            String parameter = request.getParameter(s);
            System.out.println(parameter);
            if (StringUtils.isEmpty(parameter)) {
                JSONObject jsonObject = new JSONObject();
                //这个地方是定义缺少参数或者参数为空的时候返回的数据
                jsonObject.put("status", 400);
                jsonObject.put("msg", "拦截器：缺少必要的" + s + "值");
                response.setHeader("Content-type", "application/json;charset=UTF-8");
                response.setHeader("Access-Control-Allow-Origin", "*");//跨域
                response.getWriter().write(jsonObject.toJSONString());
                return false;
            }
        }
        return true;
    }

    /**
     * 获取使用了该注解的参数名称
     */
    private List<String> getParamsName(HandlerMethod handlerMethod) {
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        List<String> list = new ArrayList<>();
        for (Parameter parameter : parameters) {
            //判断这个参数时候被加入了 ParamsNotNull. 的注解
            //.isAnnotationPresent()  这个方法可以看一下
            if (parameter.isAnnotationPresent(ParamsNotNull.class)) {
                list.add(parameter.getName());
            }
        }
        return list;
    }

    @Target({ElementType.PARAMETER})//参数级别
    @Retention(RetentionPolicy.RUNTIME) //注解保留到运行阶段
    public @interface ParamsNotNull {
    }
}