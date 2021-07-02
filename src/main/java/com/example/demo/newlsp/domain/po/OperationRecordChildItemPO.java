package com.example.demo.newlsp.domain.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: lingshipu
 * @description: 操作记录商品list表
 * @author: QWS
 * @create: 2021-02-19 22:21
 */

@Data
@Entity
@Table(name = "operationRecordChildItem")
public class OperationRecordChildItemPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int uuid;

    /**
     * 操作唯一序列号
     */
    String operationRecordSerial;

    /**
     * 商品编号
     */
    private int itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品单价
     */
    private String itemPrice;
    /**
     * 总未投放库存
     */
    private int inventoryBalance;
    /**
     * 总已投放库存
     */
    int stockCurrentAll;
    /**
     * 总销量
     */
    double salesVolumeAll;
    /**
     * 商品图片
     */
    String itemPicUrl;
}
