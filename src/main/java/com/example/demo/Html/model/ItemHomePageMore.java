package com.example.demo.Html.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ：Q
 * @date ：Created in 2019/4/1 15:44
 * @description：${description}
 */
@Entity
@Data
@Table(name="vx_homepage_more")
public class ItemHomePageMore{
    /**
     * 商品编号
     */
    @Id
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
     * 商品图片
     */
    String item_pic_url;
    /**
     * 父类店铺id
     */
    int parentShopId;
    /**
     * 库存上限
     */
    int stockMix;
    /**
     * 当前库存
     */
    int stockCurrent;
    /**
     * 销售额
     */
    double salesVolume;
}
