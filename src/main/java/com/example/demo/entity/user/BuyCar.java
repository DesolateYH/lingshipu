package com.example.demo.entity.user;

import lombok.Data;

/**
 * @author ：Q
 * @date ：Created in 2019/5/6 14:10
 * @description：
 */
@Data
public class BuyCar {
    String item_name;
    double item_price;
    int item_id;
    int buy_number;
    String item_pic_url;
    boolean check;
    String types;
}
