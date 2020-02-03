package com.example.demo.Logic.user;


import com.example.demo.db.DbManage;
import com.example.demo.entity.item.ItemDetailed;
import com.example.demo.entity.item.ItemSort;
import com.example.demo.entity.user.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LOrder {
    public Map getPendingPayment(String session_key) {
        String sqlTxt = "SELECT user_order.item_id, user_order.order_number, user_order.order_state ,user_address.vx_user_address, user_address.vx_user_consignee, user_address.vx_user_telephone," +
                "vx_homepage_more.item_name, user_order.buy_number FROM user_order, vx_homepage_more ,user_address WHERE" +
                "( user_order.item_id = vx_homepage_more.item_id ) AND user_order.vx_user_openid = " +
                "( SELECT vx_user_openid FROM vx_user WHERE session_key = '" + session_key + "')";
        List<Order> orders = new ArrayList<Order>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        for (Map<String, Object> map1 : list) {
            Order order = new Order();
            order.setItem_id(map1.get("item_id").toString());
            order.setItem_name(map1.get("item_name").toString());
            order.setVx_user_address(map1.get("vx_user_address").toString());
            order.setBuy_number(map1.get("buy_number").toString());
            order.setConsignee(map1.get("vx_user_consignee").toString());
            order.setOrder_number(map1.get("order_number").toString());
            order.setTelephone(map1.get("vx_user_telephone").toString());
            order.setOrder_state(map1.get("order_state").toString());

            orders.add(order);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", orders);
        return map;
    }

    //购买商品
    public boolean buyoneItem(String item_name, String session_key, String item_id, String address_id, int buynumber, String item_price) {
        String sqlTxt = "INSERT INTO user_order (item_name， item_id, address_id, buy_number, item_price, vx_user_openid ) value('"
                + item_name + "','"
                + item_id + "','"
                + address_id + "','"
                + buynumber + "','"
                + item_price + "',"
                + "(SELECT vx_user_openid  from vx_user WHERE session_key = '"
                + session_key + "'))";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        jdbcTemplate.update(sqlTxt);
        return true;
    }

    public Map getPendingPayment2(String session_key) {
        Map<String, Object> map = new HashMap<String, Object>();

        for (int i = 1; i < 6; i++) {
            String sqlTxt = "SELECT vx_homepage_more.item_price,user_order.item_id, user_order.order_number, user_order.order_state ,user_address.vx_user_address, user_address.vx_user_consignee, user_address.vx_user_telephone," +
                    "vx_homepage_more.item_name, user_order.buy_number FROM user_order, vx_homepage_more ,user_address WHERE" +
                    "( user_order.item_id = vx_homepage_more.item_id ) AND user_order.vx_user_openid = " +
                    "( SELECT vx_user_openid FROM vx_user WHERE session_key = '" + session_key + "')" +
                    "AND user_order.order_state = '" + i + "'" + "AND ( user_order.address_id = user_address.address_id )";
            List<Order> orders = new ArrayList<Order>();
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            DbManage db = new DbManage();
            jdbcTemplate.setDataSource(db.getDataSource());
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
            if (list == null) continue;
            for (Map<String, Object> mapbl : list) {
                Order order = new Order();
                order.setItem_price(mapbl.get("item_price").toString());
                order.setItem_id(mapbl.get("item_id").toString());
                order.setItem_name(mapbl.get("item_name").toString());
                order.setVx_user_address(mapbl.get("vx_user_address").toString());
                order.setBuy_number(mapbl.get("buy_number").toString());
                order.setConsignee(mapbl.get("vx_user_consignee").toString());
                order.setOrder_number(mapbl.get("order_number").toString());
                order.setTelephone(mapbl.get("vx_user_telephone").toString());
                order.setOrder_state(mapbl.get("order_state").toString());
                orders.add(order);
                switch (i) {
                    case 1:
                        map.put("daifukuan", orders);
                        break;
                    case 2:
                        map.put("daifahuo", orders);
                        break;
                    case 3:
                        map.put("daishouhuo", orders);
                        break;
                    case 4:
                        map.put("daipingjia", orders);
                        break;
                    case 5:
                        map.put("tuihuo", orders);
                        break;
                }
            }
        }
        return map;
    }
}
