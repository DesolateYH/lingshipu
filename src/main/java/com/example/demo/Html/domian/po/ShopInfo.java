package com.example.demo.Html.domian.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: lingshipu
 * @description: 店铺信息表
 * @author: QWS
 * @create: 2020-03-01 19:45
 */

@Entity
@Data
@Table(name = "shop_info")
public class ShopInfo {
    /**
     * 店铺编号
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int shopId;

    /**
     * 店铺地址
     */
    String shopAddress;

    /**
     * 用户数量
     */
    int userNum;

    /**
     * 销售总额
     */
    String totalSales;

}
