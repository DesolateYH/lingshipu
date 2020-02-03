package com.example.demo.entity.user;

import lombok.Data;

@Data
public class Order {
    private String item_id;
    private String order_number;
    private String vx_user_address;
    private String Consignee;
    private String telephone;
    private String item_name;
    private String buy_number;
    private String order_state;
    private String item_price;
}
