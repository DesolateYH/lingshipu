package com.example.demo.Logic.login;


import com.example.demo.db.DbManage;
import com.example.demo.entity.user.Vx_User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LVx_User {
    public List<Vx_User> queryCourse(String sqlTxt)
            throws ClassNotFoundException {
        List<Vx_User> vx_users = new ArrayList<Vx_User>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        for(Map<String, Object> map : list){
            Vx_User vx_user = new Vx_User();
            vx_user.setVx_user_openid(map.get("vx_user_openid").toString());
            vx_user.setVx_user_name(map.get("vx_user_name").toString());
            vx_user.setVx_user_money(map.get("vx_user_money").toString());
            vx_user.setVx_user_address(map.get("vx_user_address").toString());
            vx_users.add(vx_user);
        }
        return  vx_users;
    }

    //若数据库中没有现有的openid，则增加新的openid
    //线路1**步骤2**接收参数，检测是否存在现有的openid，若不存在，则增加
    public void relay(String openid,String name){
        boolean flag = this.checkUserInfo(openid,name);
        if(flag == false){
            this.addUser(openid);
        }
    }

    //若数据库中没有现有的openid，则增加新的openid
    //线路1**步骤3**遍历数据库中现有的openid
    private boolean checkUserInfo(String vx_user_openid, String vx_user_name){
        String sqlTxt = "select * from vx_user" +
                " where vx_user_openid='" + vx_user_openid + "'";
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

    //若数据库中没有现有的openid，则增加新的openid
    //线路1**步骤4**增加新的openid
    //线路1结束
    private void addUser(String vx_user_openid){
        String sqlTxt = "insert into vx_user (vx_user_openid)"
                + "values("
                + "'" + vx_user_openid + "')";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        int i = 0;
        i = jdbcTemplate.update(sqlTxt);
    }

}
