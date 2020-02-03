package com.example.demo.controller.CUser;

import com.example.demo.Logic.user.LAddress;
import com.example.demo.Logic.user.LUser_Address;
import com.example.demo.db.DbManage;
import com.example.demo.entity.user.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CAddress {
    @Autowired
    private LAddress lAddress;

    //增加用户地址
    @PostMapping("/updateuserinfo")
    public boolean updateuserinfo(String latitude, String longitude, String address, String address_name, String session_key,String telephone,String consignee) {
        if(latitude != null || longitude != null || session_key != null || address != null || address_name != null) {
            return lAddress.updateuserinfo(latitude, longitude, address, address_name, session_key,telephone,consignee);
        }{
            return false;
        }
    }

    //查询用户地址
    @RequestMapping ("/seach_user_address")
    public Map<String, Object> seach_user_address(String session_key) {
        return lAddress.seach_user_address(session_key);
    }

    //删除用户地址 这里有bug！！！！要传入session_key
    @PostMapping("/deleteAddress")
    public boolean deleteAddress(String address_id){
       return lAddress.deleteAddress(address_id);
    }
}
