package com.example.demo.controller.old;/*
package com.example.demo.controller;

import com.example.demo.Logic.login.LVx_User;
import com.example.demo.Logic.item.LQueryItem;
import com.example.demo.entity.login.Vx_User;
import com.example.demo.entity.item.ItemSort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class search {


    public Map<String, Object> search(String sqlTxt, String form) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ItemSort> member1 = null;
        List<Vx_User> member2 = null;
        LQueryItem test1 = new LQueryItem();
        LVx_User test2 = new LVx_User();
        try {
            if (form.equals("vx_homepage_more")) {
                member1 = test1.queryitems(sqlTxt);
                map.put("list", member1);
            } else if (form.equals("vx_user")) {
                member2 = test2.queryCourse(sqlTxt);
                map.put("list", member2);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

}
*/
