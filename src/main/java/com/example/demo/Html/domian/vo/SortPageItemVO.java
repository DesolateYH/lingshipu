package com.example.demo.Html.domian.vo;

import lombok.Data;

/**
 * @program: lingshipu
 * @description: 分类下的商品
 * @author: QWS
 * @create: 2020-05-12 05:41
 */
@Data
public class SortPageItemVO {
    /**
     * id
     */
    int itemId;

    /**
     * 价格
     */
    float itemPrice;

    /**
     * 当前库存
     */
    int itemStockCurrent;

    /**
     * 属性
     */
    int itemAttribute;

    /**
     * 图片地址
     */
    String itemPhotoAddress;

    /**
     * 名称
     */
    String itemName;
}
