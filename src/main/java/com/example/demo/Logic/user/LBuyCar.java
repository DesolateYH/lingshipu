package com.example.demo.Logic.user;

import com.example.demo.db.DbManage;
import com.example.demo.entity.item.ItemDetailed;
import com.example.demo.entity.item.ItemSort;
import com.example.demo.entity.item.SortList;
import com.example.demo.entity.user.BuyCar;
import com.example.demo.entity.user.Order;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Q
 * @date ：Created in 2019/5/6 14:11
 * @description：
 */
@Service
public class LBuyCar {
    public String getBuyCar(String openid) {
        String sqlTxt = "select item_json from new_item_order where vx_user_openid = '" + openid + "';";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        /* Map<String, Object> map = new HashMap<String, Object>();*/
        String item_json = null;
        for (Map<String, Object> buycar : list) {
            item_json = buycar.get("item_json").toString();
        }
        return item_json;
    }

    //获取现在的商品名称、价格、图片
    public ItemSort getItemNamePricePic(String item_id) {
        String sqlTxt = "SELECT item_name,item_price,item_pic_url from vx_homepage_more where item_id = " + item_id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        ItemSort itemSort = new ItemSort();
        for (Map<String, Object> map1 : list) {
            itemSort.setPic_url(map1.get("item_pic_url").toString());
            itemSort.setName(map1.get("item_name").toString());
            itemSort.setPrice(map1.get("item_price").toString());
        }
        return itemSort;
    }

    /**
     * 通过购物车购买
     * @param openId
     * @param item_json
     * @return
     */
    public boolean addInfoToBuyCar(String openId,String item_json) {
        try {
            String sqlTxt = "INSERT INTO new_item_car (vx_user_openid,item_json) VALUES ('"
                    + openId + "','"
                    + item_json + "')";
            System.out.println(sqlTxt);
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            DbManage db = new DbManage();
            jdbcTemplate.setDataSource(db.getDataSource());
            jdbcTemplate.update(sqlTxt);
            return true;
        } catch (Exception exception) {
            return false;
        }

    }

}


