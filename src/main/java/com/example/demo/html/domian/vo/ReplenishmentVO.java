package com.example.demo.html.domian.vo;

import lombok.Data;

/**
 * @program: lingshipu
 * @description: 用户补货清单
 * @author: QWS
 * @create: 2020-06-03 01:02
 */
@Data
public class ReplenishmentVO {
    /**
     * 名称
     */
    String replenishmentItemName;
    /**
     * id
     */
    int replenishmentItemId;
    /**
     * 数量
     */
    int replenishmentItemNum;
    /**
     * 寝室地址
     */
    String replenishmenShopAddress;
    /**
     * 价格
     */
    String replenishmentItemPrice;
    /**
     * 状态码
     */
    int replenishmentStatusCode;
}
