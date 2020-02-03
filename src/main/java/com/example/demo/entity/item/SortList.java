package com.example.demo.entity.item;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortList {

    /*  三级json
   static class Contact {

        public Contact() {
        }
        public String phone;
        public String email;
    }*/

    @Data
    public static class children {
        private int item_id;
        private int child_id;
        private String name;
        private String image;
        //public Contact contact;**三级json
    }

    @Data
    public static class Group {
        private int sort_id;
        private String sort_name;
        private boolean ishaveChild;
        private List<children> children = new ArrayList<children>();
    }

    @Data
    public static class Sort_page {
        private int sort_id;
        private String sort_name;
        private int ishaveChild;
        //public List<Object> children = new ArrayList<Object>();
        private Map<String,Object> children = new HashMap<String,Object>();
    }

    @Data
    public static class Item {
        private int item_id;
        private String item_name;
        private String item_pic_url;
    }

}
