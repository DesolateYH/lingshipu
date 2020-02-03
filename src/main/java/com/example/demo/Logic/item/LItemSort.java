package com.example.demo.Logic.item;

import com.example.demo.db.DbManage;
import com.example.demo.entity.item.ItemSort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LItemSort {
    //物品排序
    public Map<String, Object> LSort_Commodities(String method) {
        Map<String, Object> map = new HashMap<String, Object>();
        String flag = null;
        if("priceup".equals(method)){
            flag = "item_price ASC";
        }else if("pricedown".equals(method)){
            flag = "item_price DESC";
        }
        String sqlTxt = "select item_id,item_name,item_pic_url,item_price,item_type from vx_homepage_more order by " + flag;
        List<ItemSort> itemSorts = new ArrayList<ItemSort>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        for(Map<String, Object> map1 : list){
            ItemSort ItemSort = new ItemSort();
            ItemSort.setId(map1.get("item_id").toString());
            ItemSort.setName(map1.get("item_name").toString());
            ItemSort.setPic_url(map1.get("item_pic_url").toString());
            ItemSort.setPrice(map1.get("item_price").toString());
            ItemSort.setType(map1.get("item_type").toString());
            itemSorts.add(ItemSort);
        }
        map.put("list", itemSorts);
        return map;
    }
}
