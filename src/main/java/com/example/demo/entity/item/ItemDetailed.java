package com.example.demo.entity.item;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailed {
    @Data
    public static class item_detailed {
        private String item_id;
        private String changtu;
        private String shengchangriqi;
        private String baozhiqi;
        private List<child> child = new ArrayList<child>();
        /*public String tupian1;
        public String tupian2;
        public String tupian3;*/
        private String guoqishijian;
        private String item_name;
        private String item_price;
        private String item_type;
    }

    @Data
    public static class child{
        private String tupian1;
        private String tupian2;
        private String tupian3;
    }


}
