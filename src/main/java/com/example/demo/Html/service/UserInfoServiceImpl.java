package com.example.demo.Html.service;

import com.example.demo.Html.repository.AdminInfoDao;
import com.example.demo.Html.repository.UserInfoDao;
import com.example.demo.Html.domian.po.AdminInfoModel;
import com.example.demo.Html.domian.po.ShopInfo;
import com.example.demo.Html.domian.po.UserInfoModel;
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
     * @param user_id 用户名
     * @return
     */
    public AdminInfoModel findByUserId(String user_id) {
        return adminInfoDao.findByUserId(user_id);
    }


    /**
     * 获取管理员有权限的寝室列表
     *
     * @param user_id 用户名
     * @return 寝室id
     */
    public List<ShopInfo> findShopInfoByAdminPermissions(String user_id) {
        String permissions = adminInfoDao.findByUserId(user_id).getPermissions();
        if ("-1".equals(permissions)) {
            return shopInfoService.findAllShopInfo();
        }
        String[] per = permissions.split("\\|");
        List<ShopInfo> list = new ArrayList<>();
        for (String s : per) {
            ShopInfo shopInfo = shopInfoService.findShopInfoByShopId(Integer.parseInt(s));
            list.add(shopInfo);
        }
        return list;
    }

    /**
     * 获取管理员权限列表
     *
     * @param user_id 用户名
     * @return
     */
    public String getAdminPermissions(String user_id) {
        return adminInfoDao.findByUserId(user_id).getPermissions();
    }

    /**
     * 删除管理员中的寝室权限
     *
     * @param shop_id 寝室id
     * @return
     */
    public boolean deletePermissionsForAdmin(String shop_id) {
        try {
            List<AdminInfoModel> adminInfoModels = adminInfoDao.findAll();
            //遍历所有管理员
            for (AdminInfoModel v : adminInfoModels) {
                if("-1".equals(v.getPermissions()) || "NULL".equals(v.getPermissions())){
                    continue;
                }
                String[] per = v.getPermissions().split("\\|");
                //遍历单个管理员每一个权限
                StringBuilder permissionsNew = new StringBuilder("");
                for (String x : per) {
                    if (shop_id.equals(x)) {
                        continue;

                    }
                    permissionsNew.append(x).append("|");
                }
                v.setPermissions(permissionsNew.toString());
                if("".equals(v.getPermissions())){
                    v.setPermissions("NULL");
                }
                adminInfoDao.save(v);

            }
            return true;
        }catch (Exception e){
            System.out.println("Error:UserInfoServiceImpl::deletePermissionsForAdmin");
            return false;
        }
    }


}
