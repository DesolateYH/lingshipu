package com.example.demo.Logic.item;

import com.example.demo.db.DbManage;
import com.example.demo.entity.item.ItemDetailed;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

public class LItemDetailed {
    //查询物品详情
    public Map<String, Object> Litemdetailed(String item_id) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
        String format = "yyyy-MM-dd";

        String sqlTxt = "select * from item_detailed,vx_homepage_more  where item_detailed.item_id = '" + item_id + "'" +
                "and vx_homepage_more.item_id = '" + item_id + "';";
        List<ItemDetailed.item_detailed> item_detaileds = new ArrayList<ItemDetailed.item_detailed>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);

        Map userInfo = new HashMap();
        for (Map<String, Object> map : list) {
            ItemDetailed.child child = new ItemDetailed.child();
            userInfo.put("item_name", map.get("item_name").toString());
            userInfo.put("changtu", map.get("changtu").toString());
            userInfo.put("item_id", map.get("item_id").toString());
            userInfo.put("item_price", map.get("item_price").toString());
            userInfo.put("item_type", map.get("item_type").toString());
            userInfo.put("baozhiqi", map.get("baozhiqi").toString());
            userInfo.put("shengchangriqi", map.get("shengchangriqi").toString());
         /*   userInfo.put("tupian1", map.get("tupian1").toString());
            userInfo.put("tupian2", map.get("tupian2").toString());
            userInfo.put("tupian3", map.get("tupian3").toString());*/

            child.setTupian1(map.get("tupian1").toString());
            child.setTupian2(map.get("tupian2").toString());
            child.setTupian3(map.get("tupian3").toString());

            userInfo.put("zhaopian",child);

            Date date1 = new SimpleDateFormat(format).parse(map.get("shengchangriqi").toString());
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date1);
            String dateStr1 = dateAddMonth(dateStr, Integer.parseInt(map.get("baozhiqi").toString()));
            userInfo.put("guoqishijian", dateStr1);

        }

       /* for (Map<String, Object> map : list) {
            item_detailed item_detailed = new item_detailed();
            item_detailed.setItem_name(map.get("item_name").toString());
            item_detailed.setItem_price(map.get("item_price").toString());
            item_detailed.setItem_type(map.get("item_type").toString());
            item_detailed.setItem_id(map.get("item_id").toString());
            item_detailed.setBaozhiqi(map.get("baozhiqi").toString() + "天");
            item_detailed.setShengchangriqi(map.get("shengchangriqi").toString());
            System.out.println(map.get("shengchangriqi").toString());

            Date date1 = new SimpleDateFormat(format).parse(map.get("shengchangriqi").toString());
            System.out.println(date1);
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date1);
            String dateStr1 = dateAddMonth(dateStr, Integer.parseInt(map.get("baozhiqi").toString()));
            System.out.println(dateStr1);

            item_detailed.setGuoqishijian(dateStr1);
            item_detailed.setTupian1(map.get("tupian1").toString());
            item_detailed.setTupian2(map.get("tupian2").toString());
            item_detailed.setTupian3(map.get("tupian3").toString());
            item_detaileds.add(item_detailed);
        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list",userInfo);
//        map.put("list", item_detaileds);
//        map.put("list", userInfo);
        return map;
    }
    //日期增加，根据保质期和生产日期推算出过期时间
    private static String dateAddMonth(String str,int day) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(str);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_YEAR, day);// 日期加3个月
        // rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

}
