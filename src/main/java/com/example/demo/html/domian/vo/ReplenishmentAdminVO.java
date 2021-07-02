package com.example.demo.html.domian.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: lingshipu
 * @description: 查询补货
 * @author: QWS
 * @create: 2020-06-03 18:05
 */
@Data
public class ReplenishmentAdminVO {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int replenishmentItemId;
    /**
     * 寝室地址
     */
    String replenishmentShopAddress;

    /**
     * 价格
     */
    String replenishmentAllPrice;

    /**
     * 信息
     */
    List<ReplenishmentVO> replenishmentVOList;

    /**
     * 创建时间
     */
    Timestamp CreatTime;

    /**
     * 是否完成
     */
    int isFinish;
    /**
     * 状态码
     */
    int replenishmentStatusCode;
}
