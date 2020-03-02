package com.example.demo.Html.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: lingshipu
 * @description: 寝室用户信息表
 * @author: QWS
 * @create: 2020-03-01 21:39
 */
@Data
@Table(name  = "user_info")
@Entity
public class UserInfoModel {
    /**
     * 用户id
     */
    @Id
    int userId;
    /**
     * 用户名字
     */
    String userName;
    /**
     * 用户寝室号
     */
    String userAddress;
    /**
     * 用户性别
     */
    String userGerder;
    /**
     * 购买次数
     */
    int buyNum;
    /**
     * 购物总金额
     */
    double shoppingAmount;
}
