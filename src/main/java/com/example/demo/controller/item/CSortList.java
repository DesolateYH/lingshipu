package com.example.demo.controller.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.Logic.item.LSortList;
import com.example.demo.db.DbManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class CSortList {

     @Autowired
    private LSortList lSortList;
    //物品大类展示
    @RequestMapping(value = "/classify_list", method = RequestMethod.POST)
    public Map<String,Object> classify_list() {
        return lSortList.Lsortlist();
    }

    //显示首页
    @PostMapping("/homepage")
    public Map<String,Object>homepage(){
       return lSortList.showhomepage();
    }

}
