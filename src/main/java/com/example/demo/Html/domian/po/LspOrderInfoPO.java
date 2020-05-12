package com.example.demo.Html.domian.po;

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
@Table(name="order_info")
public class LspOrderInfoPO {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int orderId;
    /**
     * 订单号
     */
    String orderNumber;
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
    float orderPrice;
}
