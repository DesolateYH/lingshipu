package com.example.demo.Html.dao;

import com.example.demo.Html.model.AdminInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: lingshipu
 * @description: 管理员接口类
 * @author: QWS
 * @create: 2020-03-05 19:07
 */
public interface AdminInfoDao extends JpaRepository<AdminInfoModel,Integer> {
    /**
     * 获取管理员信息
     * @param i 用户名
     * @param p 密码
     * @return
     */
    AdminInfoModel findByUserIdAndPassword(String i,String p);

    /**
     * 获取管理员信息
     * @param i 用户名
     * @return
     */
    AdminInfoModel findByUserId(String i);
}
