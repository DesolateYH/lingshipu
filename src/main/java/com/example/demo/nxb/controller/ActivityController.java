package com.example.demo.nxb.controller;

import com.example.demo.Logic.user.LUser_Address;
import com.example.demo.nxb.model.ActivityDetailed;
import com.example.demo.nxb.service.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ActivityController {
    @Autowired
    ActivityServiceImpl activityService;
    @Autowired
    LUser_Address lUser_address;
    //获取所有活动
    @RequestMapping(value = "/nxb/getAllActivity")
    public Map<String, Object> getAllActivityForNxb(){
        return activityService.getAllActivityForNxb();
    }

    //获取活动详情
    @RequestMapping(value = "/nxb/getAllActivityDetailed")
    public Map<String,Object> getAllActivityDetailedForNxb(int id){
        return activityService.getAllActivityDetailedForNxb(id);
    }

    //添加活动预约
    @RequestMapping(value = "/nxb/addActivityForUser")
    public boolean addActivityForUser(String session,Integer activityId){
        String openid = lUser_address.getOpenid_in_LUser_Address(session);
        if(!"null".equals(openid)){
            return activityService.addActivityForUser(openid,activityId);
        }else{
            return false;
        }

    }
}
