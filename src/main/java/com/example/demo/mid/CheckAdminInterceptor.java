//package com.example.demo.mid;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.demo.dbm.DbManage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//import java.lang.reflect.Parameter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @program: lingshipu
// * @description: 检查权限拦截器
// * @author: QWS
// * @create: 2021-04-24 22:08
// */
//public class    CheckAdminInterceptor extends HandlerInterceptorAdapter {
//    private static final Logger logger = LoggerFactory.getLogger(CheckAdminInterceptor.class);
//
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) {
//            logger.warn("UnSupport handler");
//            return true;
//        }
//        List<String> list = getParamsName((HandlerMethod) handler);
//        for (String s : list) {
//            String parameter = request.getParameter(s);
//            JdbcTemplate jdbcTemplate = new JdbcTemplate();
//            DbManage db = new DbManage();
//            jdbcTemplate.setDataSource(db.getDataSource());
//            String sqlTxt = "SELECT user_info.admin_id FROM `user_info` " +
//                    "where user_info.openid = (select vx_user_openid FROM `vx_user` WHERE session_key = '" + parameter + "')";
//            List<Map<String, Object>> jdbcList = jdbcTemplate.queryForList(sqlTxt);
//            String permission = null;
//            for (Map<String, Object> map : jdbcList) {
//                permission = map.get("admin_id").toString();
//            }
//            if(permission==null){
//                JSONObject jsonObject = new JSONObject();
//                //这个地方是定义缺少参数或者参数为空的时候返回的数据
//                jsonObject.put("status", 403);
//                jsonObject.put("msg", "拦截器：token无效");
//                response.setHeader("Content-type", "application/json;charset=UTF-8");
//                response.setHeader("Access-Control-Allow-Origin", "*");//跨域
//                response.getWriter().write(jsonObject.toJSONString());
//                return false;
//            }
//            if ("0".equals(permission)) {
//                JSONObject jsonObject = new JSONObject();
//                //这个地方是定义缺少参数或者参数为空的时候返回的数据
//                jsonObject.put("status", 401);
//                jsonObject.put("msg", "拦截器：权限不足");
//                response.setHeader("Content-type", "application/json;charset=UTF-8");
//                response.setHeader("Access-Control-Allow-Origin", "*");//跨域
//                response.getWriter().write(jsonObject.toJSONString());
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 获取使用了该注解的参数名称
//     */
//    private List<String> getParamsName(HandlerMethod handlerMethod) {
//        Parameter[] parameters = handlerMethod.getMethod().getParameters();
//        List<String> list = new ArrayList<>();
//        for (Parameter parameter : parameters) {
//            //判断这个参数时候被加入了 ParamsNotNull. 的注解
//            //.isAnnotationPresent()  这个方法可以看一下
//            if (parameter.isAnnotationPresent(CheckAdminInterceptor.CheckAdmin.class)) {
//                list.add(parameter.getName());
//            }
//        }
//        return list;
//    }
//
//    @Target({ElementType.PARAMETER})//参数级别
//    @Retention(RetentionPolicy.RUNTIME) //注解保留到运行阶段
//    public @interface CheckAdmin {
//    }
//}
