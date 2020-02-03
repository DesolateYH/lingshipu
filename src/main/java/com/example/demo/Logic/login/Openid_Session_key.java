package com.example.demo.Logic.login;

import com.example.demo.db.DbManage;
import org.springframework.jdbc.core.JdbcTemplate;

public class Openid_Session_key {
    //将openid和session_key关联起来，若已经存在上一个session_key，则更新session_key
    //线路2**步骤2**数据库操作层
    //线路2结束
    public void correlation_session_key_openid(String openid, String session_key) {
        String sqlTxt = "insert into vx_user (vx_user_openid,session_key)"
                + "values('" + openid + "','" + session_key + "') ON DUPLICATE KEY UPDATE session_key "
                + "='" + session_key + "';";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        int i = 0;
        i = jdbcTemplate.update(sqlTxt);
    }
}
