package com.example.demo.nxb.service;

import com.example.demo.db.DbManage;
import com.example.demo.entity.item.ItemSort;
import com.example.demo.nxb.dao.ActivityIdOpenidDao;
import com.example.demo.nxb.model.Activity;
import com.example.demo.nxb.model.ActivityDetailed;
import com.example.demo.nxb.model.ActivityIdOpenid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ActivityServiceImpl {
    @Autowired
    ActivityIdOpenidDao activityIdOpenidDao;

    //获取所有活动概况
    public Map<String, Object> getAllActivityForNxb() {
        String sqlTxt = "SELECT nxb_activity.activity_title,nxb_activity.activity_description,nxb_activity.sort," +
                "nxb_activity.activity_deadline,nxb_activity.id,nxb_activity.activity_image_address," +
                "nxb_activity_detailed.activity_people_number,nxb_activity_detailed.activity_current_appointments_number " +
                "FROM nxb_activity,nxb_activity_detailed WHERE nxb_activity.id = nxb_activity_detailed.id";
        List<Activity> activities = new ArrayList<Activity>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        List<Activity> lists1 = new ArrayList<>();
        List<Activity> lists2 = new ArrayList<>();
        List<Activity> lists3 = new ArrayList<>();
        for (Map<String, Object> map1 : list) {
            Activity activity = new Activity();
            double activity_current_appointments_number = Integer.parseInt(map1.get("activity_current_appointments_number").toString());
            double activity_people_number = Integer.parseInt(map1.get("activity_people_number").toString());
           /* activity.setActivity_people_number(Integer.parseInt(map1.get("activity_people_number").toString()));
            activity.setActivity_current_appointments_number(Integer.parseInt(map1.get("activity_current_appointments_number").toString()));*/
            activity.setId(Integer.parseInt(map1.get("id").toString()));
            activity.setRatio(activity_current_appointments_number / activity_people_number * 100.0);
            activity.setActivityDescription(map1.get("activity_description").toString());
            activity.setSort(Integer.parseInt(map1.get("sort").toString()));
            activity.setActivityImageAddress(map1.get("activity_image_address").toString());
            activity.setActivityTitle(map1.get("activity_title").toString());
            activity.setActivityDeadline(changeTime (Timestamp.valueOf(map1.get("activity_deadline").toString()).toString()) );
            if (activity.getSort() == 1) {
                lists1.add(activity);
            } else if (activity.getSort() == 2) {
                lists2.add(activity);
            } else if (activity.getSort() == 3) {
                lists3.add(activity);
            }

        }
        map.put("lygg", lists1);
        map.put("syty", lists2);
        map.put("zyfw", lists3);

        return map;
    }

    //获取单个活动详情
    public Map<String,Object> getAllActivityDetailedForNxb(int id) {
        String sqlTxt = "SELECT nxb_activity.activity_title,nxb_activity_detailed.id,nxb_activity_detailed.activity_time,nxb_activity_detailed.activity_status," +
                "nxb_activity_detailed.activity_people_number,nxb_activity_detailed.activity_location,nxb_activity_detailed.activity_current_appointments_number " +
                "FROM nxb_activity_detailed,nxb_activity WHERE nxb_activity_detailed.id = " + id + " and nxb_activity.id = " + id;
        System.out.println(sqlTxt);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        Map map = new HashMap();
        for (Map<String, Object> map1 : list) {
            map.put("activity_title", map1.get("activity_title").toString());
            map.put("activity_location", map1.get("activity_location").toString());
            map.put("activity_people_number", map1.get("activity_people_number").toString());
            map.put("activity_current_appointments_number", map1.get("activity_current_appointments_number").toString());
            map.put("activity_status", map1.get("activity_status").toString());
            map.put("id", map1.get("id").toString());
            map.put("activity_time", map1.get("activity_time").toString());
            map.put("activity_plan",getActivityPlan(map1.get("id").toString()));

       /*     activity.setActivityLocation((map1.get("activity_location").toString()));
            activity.setActivityPeopleNumber(Integer.parseInt(map1.get("activity_people_number").toString()));
            activity.setActivityCurrentAppointmentsNumber(Integer.parseInt(map1.get("activity_current_appointments_number").toString()));
            activity.setActivityStatus(Integer.parseInt(map1.get("activity_status").toString()));
            activity.setId(Integer.parseInt(map1.get("id").toString()));
            activity.setActivityTime((map1.get("activity_time").toString()));*/
       /*     lists.add(activity);*/
        }
        return map;
    }

    private String changeTime(String date) {
        String strm = date.substring(0,date.length()-2);
        return strm;
    }

    //获取活动详情时间安排
    private List<String> getActivityPlan(String id){
        String sqlTxt = "SELECT activity_plan FROM nxb_activity_detailed WHERE id = " + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        String activityplan = null;
        for (Map<String, Object> map1 : list) {
             activityplan = map1.get("activity_plan").toString();
        }
        String[] plan = activityplan.split("@");
        List<String> planlist = Arrays.asList(plan);
        return planlist;
    }

    //将活动id与openid关联起来
    public boolean addActivityForUser(String openid,int activityId){
    /*    try{*/
            ActivityIdOpenid activityIdOpenid = new ActivityIdOpenid();
            activityIdOpenid.setActivityId(activityId);
            activityIdOpenid.setVxUserOpenid(openid);
            activityIdOpenidDao.save(activityIdOpenid);
            return true;
 /*       }catch (Exception ex){
            return false;
        }*/
    }
}
