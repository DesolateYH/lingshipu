package com.example.demo.Html.service;

import com.example.demo.Html.dao.AdminInfoDao;
import com.example.demo.Html.dao.UserInfoDao;
import com.example.demo.Html.model.AdminInfoModel;
import com.example.demo.Html.model.ShopInfo;
import com.example.demo.Html.model.UserInfoModel;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: lingshipu
 * @description: 用户信息服务类
 * @author: QWS
 * @create: 2020-03-01 21:43
 */
@Service
public class UserInfoServiceImpl {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    AdminInfoDao adminInfoDao;
    @Autowired
    ShopInfoServiceImpl shopInfoService;

    /**
     * 获取用户信息
     *
     * @param userAddress 用户寝室号
     * @return 用户信息
     */
    public List<UserInfoModel> findUserInfoByUserAddress(String userAddress) {
        return userInfoDao.findByUserAddress(userAddress);
    }

    /**
     * 获取管理员信息
     *
     * @param user_id  用户名
     * @param password 密码
     * @return
     */
    public AdminInfoModel findByUserIdAndPassword(String user_id, String password) {
        return adminInfoDao.findByUserIdAndPassword(user_id, password);
    }

    /**
     * 获取管理员信息
     *
     * @param user_id  用户名
     * @return
     */
    public AdminInfoModel findByUserId(String user_id) {
        return adminInfoDao.findByUserId(user_id);
    }


    /**
     * 获取管理员有权限的寝室列表
     *
     * @param user_id 用户名
     * @return 寝室列表
     */
    public List<ShopInfo> findShopInfoByAdminPermissions(String user_id) {
        String permissions = adminInfoDao.findByUserId(user_id).getPermissions();
        if("-1".equals(permissions)){
            return shopInfoService.findAllShopInfo();
        }
        String [] per = permissions.split("\\|");
        List<ShopInfo> list = new ArrayList<>();
        for (String s : per) {
            ShopInfo shopInfo = shopInfoService.findShopInfoByShopId(Integer.parseInt(s));
            list.add(shopInfo);
        }
        return list;
    }


}
