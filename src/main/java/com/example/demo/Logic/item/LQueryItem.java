package com.example.demo.Logic.item;

import com.example.demo.db.DbManage;
import com.example.demo.entity.item.ItemSort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LQueryItem {
    //遍历homepage_more数据库
    public List<ItemSort> queryitems(String sqlTxt)
            throws ClassNotFoundException {
        List<ItemSort> itemSorts = new ArrayList<ItemSort>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        for(Map<String, Object> map : list){
            ItemSort ItemSort = new ItemSort();
            ItemSort.setId(map.get("item_id").toString());
            ItemSort.setName(map.get("item_name").toString());
            ItemSort.setPic_url(map.get("item_pic_url").toString());
            ItemSort.setPrice(map.get("item_price").toString());
            ItemSort.setType(map.get("item_type").toString());
            itemSorts.add(ItemSort);
        }
        return itemSorts;
    }

}
