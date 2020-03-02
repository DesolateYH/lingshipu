package com.example.demo.controller.CUser;

import com.example.demo.Logic.user.LBuyCar;
import com.example.demo.Logic.user.LUser_Address;
import com.example.demo.entity.item.ItemSort;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ：Q
 * @date ：Created in 2019/5/6 14:10
 * @description：
 */
@RestController
@CrossOrigin
public class CBuyCar {
    @Autowired
    LBuyCar lBuyCar;

    /**
     * 获取购买记录
     * @param session_key
     * @return
     */
    @RequestMapping(value = "/GetItemOrder")
    public String getBuyCar(String session_key) {
        LUser_Address lUser_address = new LUser_Address();
        String openid = lUser_address.getOpenid_in_LUser_Address(session_key);
        return lBuyCar.getBuyCar(openid);
    }

    /**
     * 通过购物车购买
     * @param item_json 多级json，包含session_key（对象）和item_json(数组)
     * @return boolean
     */
    @RequestMapping(value = "/buyItemByCar")
    public boolean addBuyCar(String item_json) {

        try {
            JSONObject jObject1 = new JSONObject(item_json);
            String session_key = jObject1.getString("session_key");

            LUser_Address lUser_address = new LUser_Address();
            String openid = lUser_address.getOpenid_in_LUser_Address(session_key);


            String item_jsonText = jObject1.getString("item_json");

            if ("null".equals(openid)) {
                return false;
            } else {
                return lBuyCar.addInfoToBuyCar(openid, item_jsonText);
            }
        } catch (Exception exception) {
            return false;
        }
    }

}
