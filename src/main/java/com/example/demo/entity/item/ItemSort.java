package com.example.demo.entity.item;

import lombok.Data;

@Data
public class ItemSort {
    private String id;
    private String name;
    private String pic_url;
    private String price;
    private String type;

    @Data
    public static class homepage{

        private String id;
        private String name;
        private String pic_url;
        private String price;
        private String item_unit_price;
    }

}
