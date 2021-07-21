package com.example.demo.mid;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dbm.DbManage;
import com.example.demo.html.domian.vo.Msg;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 请求参数拦截校验
 */

@Aspect
@Component
public class AopCheckAdmin {

    @Around(value = "execution(* com.example.demo.newlsp.controller.admin..*(..)))")
    public Object demoAop(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Map<String, Object> params = getNameAndValue(proceedingJoinPoint);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if(entry.getKey().equals("access_token")){
                String access_token = String.valueOf(entry.getValue());
                JdbcTemplate jdbcTemplate = new JdbcTemplate();
                DbManage db = new DbManage();
                jdbcTemplate.setDataSource(db.getDataSource());
                String sqlTxt = "SELECT user_info.admin_id FROM `user_info` " +
                        "where user_info.openid = (select vx_user_openid FROM `vx_user` WHERE session_key = '" + access_token + "')";
                List<Map<String, Object>> jdbcList = jdbcTemplate.queryForList(sqlTxt);
                String permission = null;
                for (Map<String, Object> map : jdbcList) {
                    permission = map.get("admin_id").toString();
                }
                if(permission==null){
                    return Msg.statu400().add("error","token无效").add("origin","aop");
                }
                if ("0".equals(permission)) {
                    return Msg.statu400().add("error","权限不足").add("origin","aop");
                }
            }

        }


        return proceedingJoinPoint.proceed();
    }

    Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }
        return param;
    }

}