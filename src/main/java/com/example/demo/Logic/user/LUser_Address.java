package com.example.demo.Logic.user;

import com.example.demo.db.DbManage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LUser_Address {
    //根据session_key获取到openid
    public String getOpenid_in_LUser_Address(String session_key){
        String sqlTxt = "select vx_user_openid from vx_user where session_key = '"
                + session_key + "';";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        String openid;
        for(Map<String,Object>map:list){
            openid = map.get("vx_user_openid").toString();
            return openid;
        }
        return "null";
    }

    //检查user_address表中是否存在相应的openid
    public boolean CheckAndInsertUserInfo_inUser_Address(String vx_user_openid){
        String sqlTxt = "select vx_user_openid from user_address where vx_user_openid = '" + vx_user_openid + "';";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        int i = 0;
        for(Map<String, Object> map : list){
            i++;
        }
        boolean flag = false;
        if (i > 0)
            flag = true;
        return flag;
    }
}
