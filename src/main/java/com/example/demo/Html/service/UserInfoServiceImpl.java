package com.example.demo.Html.service;

import com.example.demo.Html.dao.UserInfoDao;
import com.example.demo.Html.model.UserInfoModel;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 获取用户信息
     * @param userAddress 用户寝室号
     * @return 用户信息
     */
    public List<UserInfoModel> findUserInfoByUserAddress(String userAddress){
        return  userInfoDao.findByUserAddress(userAddress);
    }
}
