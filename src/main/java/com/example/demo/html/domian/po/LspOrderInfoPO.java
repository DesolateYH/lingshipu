package com.example.demo.html.domian.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: lingshipu
 * @description: 订单
 * @author: QWS
 * @create: 2020-05-13 02:48
 */
@Entity
@Data
@Table(name = "order_info")
public class LspOrderInfoPO {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;
    /**
     * 时间
     */
    String orderTime;
    /**
     * 商品json
     */
    String orderItemJson;
    /**
     * openid
     */
    String vxUserOpenid;

    /**
     * 价格
     */
    String orderPrice;

    /**
     * 订单状态
     */
    int orderState;

    /**
     * 订单号
     */
    String orderOutTradeNo;

    /**
     * 付款人地址
     */
    String userAddress;

    /**
     * 寝室地址
     */
    String shopAddress;

}
