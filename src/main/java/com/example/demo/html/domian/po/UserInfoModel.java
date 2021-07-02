package com.example.demo.html.domian.po;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

/**
 * @program: lingshipu
 * @description: 寝室用户信息表
 * @author: QWS
 * @create: 2020-03-01 21:39
 */
@Data
@Table(name  = "user_info")
@Entity
@DynamicUpdate
@SelectBeforeUpdate
public class UserInfoModel {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int userId;
    /**
     * 用户名字
     */
    String userName;
    /**
     * 用户寝室id
     */
    int shopId;
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
    String shoppingAmount;

    /**
     * openid
     */
    String openid;
    /**
     * 管理员id
     */
    String adminId = String.valueOf('0');

    /**
     * 手机号
     */
    String userTelephone;
    /**
     * 楼栋
     */
    String userBuilding;
    /**
     * 楼层
     */
    String userFloor;
    /**
     * 宿舍
     */
    String userDormitory;


}
