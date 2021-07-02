package com.example.demo.newlsp.controller;

import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.repository.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.CheckParamsInterceptor.*;
import com.example.demo.CheckAdminInterceptor.*;

/**
 * @program: lingshipu
 * @description: 用户控制层
 * @author: QWS
 * @create: 2021-05-05 20:25
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoDao userInfoDao;

    /**
     * 获取用户信息
     * @param access_token token
     * @param shop_id 寝室id（为空则传全部）
     * @return 1
     */
    @RequestMapping(value = "/getAllOrByShopId")
    public Msg getAll(@ParamsNotNull @CheckAdmin String access_token,Integer shop_id){
        if(shop_id==null) {
            return Msg.statu200().add("info", userInfoDao.findAll());
        }else {
            return Msg.statu200().add("info",userInfoDao.findByShopId(shop_id));
        }
    }

}


