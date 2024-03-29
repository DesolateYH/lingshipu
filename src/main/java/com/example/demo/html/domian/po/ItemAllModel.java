package com.example.demo.html.domian.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: lingshipu
 * @description: 商品总表
 * @author: QWS
 * @create: 2020-03-03 22:02
 */
@Entity
@Data
@Table(name = "item_all")
public class ItemAllModel {
    /**
     * 商品编号
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    /**
     * 分类id
     */
    int itemSortId;
    /**
     * 商品进货价
     */
    private String itemCommodityPurchasePrice;

}
