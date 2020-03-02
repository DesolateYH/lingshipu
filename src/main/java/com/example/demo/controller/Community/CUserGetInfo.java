package com.example.demo.controller.Community;

import com.example.demo.Html.model.Item;
import com.example.demo.Html.service.ItemServiceImpl;
import com.example.demo.Logic.Community.LUserGetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CUserGetInfo {
    @Autowired
    private LUserGetInfo lUserGetInfo;
    //业主获取物业公告列表
    @GetMapping(value = "/UserGetInfoforCommunity")
    public Map UserGetInfoforCommunity(){
        return lUserGetInfo.UserGetInfoforCommunity();
    }
    //业主获取物业公告详情
    @PostMapping(value = "/UserGetInfoforCommunityDetailed")
    public Map<String, Object> UserGetInfoforCommunityDetailed(String property_info_id){
        return lUserGetInfo.UserGetInfoforCommunityDetailed(property_info_id);
    }

}
