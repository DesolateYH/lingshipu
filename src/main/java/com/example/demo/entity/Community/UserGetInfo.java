package com.example.demo.entity.Community;

import com.example.demo.entity.item.ItemDetailed;
import com.example.demo.entity.item.SortList;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserGetInfo {
    private String property_info;
    private Timestamp property_info_time;
    private String property_info_id;
    private String property_info_user;
    private String property_info_user_touxiang;
    private String property_info_tittle;
    private int property_info_replyNumber;
    private List<Property_reply> Property_reply;

    @Data
    public static class Property_reply {
        private int property_info_reply_PrimaryKey;
        private int property_info_id;
        private String property_info_reply;
        private Timestamp property_info_reply_time;
        private String property_info_reply_id;
        private String property_info_reply_user;
    }
}
