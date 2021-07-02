package com.example.demo.html;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @program: tushu
 * @description:
 * @author: QWS
 * @create: 2020-04-26 15:16
 */
public class SessionUtil {
    /**
     * 获取request
     * @return 1
     */
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes==null? null : requestAttributes.getRequest();
    }

    /**
     * 获取session
     * @return 1
     */
    public static HttpSession getSession(){
        return getRequest().getSession(false);
    }

    /**
     * 获取真实路径
     * @return 1
     */
    public static String getRealRootPath(){
        return getRequest().getServletContext().getRealPath("/");
    }

    /**
     * 获取ip
     * @return 1
     */
    public static String getIp() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if(servletRequestAttributes!=null){
            HttpServletRequest request = servletRequestAttributes.getRequest();
            return request.getRemoteAddr();
        }
        return null;
    }

    /**
     * 获取session中的Attribute
     * @param name 1
     * @return 1
     */
    public static Object getSessionAttribute(String name){
        HttpServletRequest request = getRequest();
        return request == null?null:request.getSession().getAttribute(name);
    }

    /**
     * 设置session的Attribute
     * @param name 1
     * @param value 1
     */
    public static void setSessionAttribute(String name,Object value){
        HttpServletRequest request = getRequest();
        if(request!=null){
            request.getSession().setAttribute(name, value);
        }
    }

    /**
     * 获取request中的Attribute
     * @param name 1
     * @return 1
     */
    public static Object getRequestAttribute(String name){
        HttpServletRequest request = getRequest();
        return request == null?null:request.getAttribute(name);
    }

    /**
     * 设置request的Attribute
     * @param name 1
     * @param value 1
     */
    public static void setRequestAttribute(String name,Object value){
        HttpServletRequest request = getRequest();
        if(request!=null){
            request.setAttribute(name, value);
        }
    }

    /**
     * 获取上下文path
     * @return 1
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * 删除session中的Attribute
     * @param name 1
     */
    public static void removeSessionAttribute(String name) {
        getRequest().getSession().removeAttribute(name);
    }

}


