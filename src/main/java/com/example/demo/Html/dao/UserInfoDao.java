package com.example.demo.Html.dao;

import com.example.demo.Html.model.UserInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 用户信息接口类
 * @author: QWS
 * @create: 2020-03-01 21:43
 */
public interface UserInfoDao extends JpaRepository<UserInfoModel,Integer> {
    /**
     * 获取用户信息
     * @param userAddress 寝室id
     * @return 用户信息
     */
    List<UserInfoModel> findByUserAddress(String userAddress);
}
