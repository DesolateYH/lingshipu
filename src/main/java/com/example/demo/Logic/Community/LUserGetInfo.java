package com.example.demo.Logic.Community;

import com.example.demo.db.DbManage;
import com.example.demo.entity.Community.UserGetInfo;
import org.bouncycastle.math.ec.ScaleYPointMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LUserGetInfo {
    //得到所有信息详情
    public Map UserGetInfoforCommunity() {
        this.updateAllProperty_info_replyNumber();
        String sqlTxt = "select * from property_info";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        List<UserGetInfo> userGetInfos = new ArrayList<UserGetInfo>();


        for (Map<String, Object> map1 : list) {
            UserGetInfo userGetInfo = new UserGetInfo();
            userGetInfo.setProperty_info(map1.get("property_info").toString());
            userGetInfo.setProperty_info_id(map1.get("property_info_id").toString());
//            this.updateProperty_info_replyNumber(map1.get("property_info_id").toString());
            userGetInfo.setProperty_info_user(map1.get("property_info_user").toString());
            userGetInfo.setProperty_info_user_touxiang(map1.get("property_info_user_touxiang").toString());
            userGetInfo.setProperty_info_tittle(map1.get("property_info_tittle").toString());
            userGetInfo.setProperty_info_time(Timestamp.valueOf(map1.get("property_info_time").toString()));
            userGetInfo.setProperty_info_replyNumber(Integer.parseInt(map1.get("property_info_replyNumber").toString()));

            String sqlTxt2 = "select * from property_info_reply where property_info_id = '" + map1.get("property_info_id").toString() + "'";
            List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sqlTxt2);
            List<UserGetInfo.Property_reply> list3 = new ArrayList<>();

            if ("0".equals(map1.get("property_info_replyNumber").toString())) {
                /*userGetInfo.setProperty_info_reply_user("0");
                userGetInfo.setProperty_info_reply_id("0");
                userGetInfo.setProperty_info_reply_time(Timestamp.valueOf(map1.get("property_info_reply_time").toString()));
                userGetInfo.setProperty_info_reply("0");*/
            } else {
                for (Map<String, Object> map2 : list2) {
                    UserGetInfo.Property_reply userGetInfoChild = new UserGetInfo.Property_reply();
                    userGetInfoChild.setProperty_info_id(Integer.parseInt(map2.get("property_info_id").toString()));
                    userGetInfoChild.setProperty_info_reply((map2.get("property_info_reply").toString()));
                    userGetInfoChild.setProperty_info_reply_id(map2.get("property_info_reply_id").toString());
                    userGetInfoChild.setProperty_info_reply_PrimaryKey(Integer.parseInt(map2.get("property_info_reply_PrimaryKey").toString()));
                    userGetInfoChild.setProperty_info_reply_time(Timestamp.valueOf(map2.get("property_info_reply_time").toString()));
                    userGetInfoChild.setProperty_info_reply_user(map2.get("property_info_reply_user").toString());
                    list3.add(userGetInfoChild);
                }
            }
            userGetInfo.setProperty_reply(list3);
            userGetInfos.add(userGetInfo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", userGetInfos);
        return map;
    }

    //刷新单个信息回复数
    private void updateProperty_info_replyNumber(String property_info_id) {
        String sqlTxt = "UPDATE property_info " +
                "SET property_info_replyNumber = (SELECT count(*)FROM property_info_reply WHERE property_info_id = '" + property_info_id + "')" +
                "WHERE " +
                "property_info_id = '" + property_info_id + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        jdbcTemplate.update(sqlTxt);
    }

    //刷新所有回复数
    public void updateAllProperty_info_replyNumber() {
        String sqlTxt = "select * from property_info";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        List<UserGetInfo> userGetInfos = new ArrayList<UserGetInfo>();

        for (Map<String, Object> map1 : list) {
            UserGetInfo userGetInfo = new UserGetInfo();
            userGetInfo.setProperty_info_id(map1.get("property_info_id").toString());
            this.updateProperty_info_replyNumber(map1.get("property_info_id").toString());
        }
    }

    //得到单个信息详情
    public Map<String, Object> UserGetInfoforCommunityDetailed(String property_info_id) {
        String sqlTxt = "select * from property_info where property_info_id = '" + property_info_id + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        List<UserGetInfo> userGetInfos = new ArrayList<UserGetInfo>();

        Map<String, Object> map = new HashMap<String, Object>();

        for (Map<String, Object> map1 : list) {
            UserGetInfo userGetInfo = new UserGetInfo();
            map.put("property_info", map1.get("property_info").toString());
            map.put("property_info_id", map1.get("property_info_id").toString());
            this.updateProperty_info_replyNumber(map1.get("property_info_id").toString());
            map.put("property_info_user", map1.get("property_info_user").toString());
            map.put("property_info_user_touxiang", map1.get("property_info_user_touxiang").toString());
            map.put("property_info_tittle", map1.get("property_info_tittle").toString());
            map.put("property_info_time", Timestamp.valueOf(map1.get("property_info_time").toString()));
            map.put("property_info_replyNumber", Integer.parseInt(map1.get("property_info_replyNumber").toString()));

            String sqlTxt2 = "select * from property_info_reply where property_info_id = '" + property_info_id + "'";
            List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sqlTxt2);
            List<UserGetInfo.Property_reply> list3 = new ArrayList<>();

            if ("0".equals(map1.get("property_info_replyNumber").toString())) {
                /*userGetInfo.setProperty_info_reply_user("0");
                userGetInfo.setProperty_info_reply_id("0");
                userGetInfo.setProperty_info_reply_time(Timestamp.valueOf(map1.get("property_info_reply_time").toString()));
                userGetInfo.setProperty_info_reply("0");*/
            } else {
                for (Map<String, Object> map2 : list2) {
                    UserGetInfo.Property_reply userGetInfoChild = new UserGetInfo.Property_reply();
                    userGetInfoChild.setProperty_info_id(Integer.parseInt(map2.get("property_info_id").toString()));
                    userGetInfoChild.setProperty_info_reply((map2.get("property_info_reply").toString()));
                    userGetInfoChild.setProperty_info_reply_id(map2.get("property_info_reply_id").toString());
                    userGetInfoChild.setProperty_info_reply_PrimaryKey(Integer.parseInt(map2.get("property_info_reply_PrimaryKey").toString()));
                    userGetInfoChild.setProperty_info_reply_time(Timestamp.valueOf(map2.get("property_info_reply_time").toString()));
                    userGetInfoChild.setProperty_info_reply_user(map2.get("property_info_reply_user").toString());
                    list3.add(userGetInfoChild);
                    map.put("property_reply", userGetInfoChild);
                }
            }
//            userGetInfo.setProperty_reply(list3);
            userGetInfos.add(userGetInfo);
        }


//        map.put("list", userGetInfos);
        return map;
    }
}
