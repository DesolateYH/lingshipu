package com.example.demo.html.domian;

import lombok.Data;

import javax.persistence.Id;

/**
 * @author ：Q
 * @date ：Created in 2019/4/4 11:11
 * @description：${description}
 */
@Data
public class ItemAndHomePageMoreRelation {
    @Id
    String itemId;
    String shengchangriqi;
    String baozhiqi;
    private String itemName;
    private String itemPrice;
}
