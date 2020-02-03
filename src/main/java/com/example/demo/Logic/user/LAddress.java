package com.example.demo.Logic.user;

import com.example.demo.db.DbManage;
import com.example.demo.entity.user.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LAddress {
    //增加用户地址
    public boolean updateuserinfo(String latitude, String longitude, String address, String address_name, String session_key, String vx_user_telephone, String vx_user_consignee) {
        Map map = new HashMap();
        map.put("latitude:", latitude);
        map.put("longitude:", longitude);
        map.put("session_key:", session_key);
        map.put("address:", address);
        map.put("address_name", address_name);
        map.put("vx_user_telephone", vx_user_telephone);
        map.put("vx_user_consignee", vx_user_consignee);

        //创建user_address逻辑层对象
        LUser_Address lUser_address = new LUser_Address();

        //根据session_key从vx_user表中获取openid
        String openid = lUser_address.getOpenid_in_LUser_Address(session_key);

        //定义一个flag，检查user_address表中是否存在相应的openid，如果有则返回true，没有则返回false
        boolean flag = lUser_address.CheckAndInsertUserInfo_inUser_Address(openid);


        //如果user_address表中没有相应openid，则新增一个openid
        /*
        if (!flag) {
            String sqlTxt = "insert into user_address (vx_user_openid) value ('" + openid + "');";
            System.out.println(sqlTxt);
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            DbManage db = new DbManage();
            jdbcTemplate.setDataSource(db.getDataSource());
            int i = 0;
            i = jdbcTemplate.update(sqlTxt);
            System.out.println(sqlTxt);
            return true;
        }else {
            */
        //根据openid将参数传入数据库，如果已经存在数据则更新
        String sqlTxt = "insert into user_address "
                + "(latitude,vx_user_consignee,longitude,vx_user_address,vx_user_address_name,vx_user_telephone,vx_user_openid) value ('"
                + latitude + "','"
                + vx_user_consignee + "','"
                + longitude + "','"
                + address + "','"
                + address_name + "','"
                + vx_user_telephone + "','"
                + openid + "') ";
        System.out.println(sqlTxt);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        int i = 0;
        i = jdbcTemplate.update(sqlTxt);
        this.deleteNullAddress();
        return true;
    }


    //查询用户地址
    public Map<String, Object> seach_user_address(String session_key) {
        deleteNullAddress();
        Map<String, Object> map = new HashMap<String, Object>();
        LUser_Address lUser_address = new LUser_Address();
        List<Address> addresses = new ArrayList<Address>();
        String openid = lUser_address.getOpenid_in_LUser_Address(session_key);
        String sqlTxt = "select address_id,vx_user_address,vx_user_address_name,vx_user_telephone,vx_user_consignee from user_address where vx_user_openid = '" + openid + "';";
        System.out.println(sqlTxt);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        for (Map<String, Object> map1 : list) {
            Address address = new Address();
            address.setAddress(map1.get("Vx_user_address").toString());
            address.setAddress_name(map1.get("Vx_user_address_name").toString());
            address.setVx_user_telephone(map1.get("vx_user_telephone").toString());
            address.setVx_user_consignee(map1.get("vx_user_consignee").toString());
            address.setAddress_id((Integer.parseInt(map1.get("address_id").toString())));
            addresses.add(address);
        }
        this.deleteNullAddress();
        map.put("list", addresses);
        return map;
    }

    //删除用户地址
    public boolean deleteAddress(String address_id) {
        deleteNullAddress();
        int address_ids = Integer.parseInt(address_id);
        String sqlTxt = "DELETE FROM user_address where address_id = " + address_ids;

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());

        int i = jdbcTemplate.update(sqlTxt);
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    private void deleteNullAddress() {
        String sqlTxt = "delete from user_address where vx_user_address is NULL";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        jdbcTemplate.update(sqlTxt);
    }
}
