package com.example.demo.controller.CUser;

import com.example.demo.Logic.user.LOrder;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class COrder {
    @Autowired
    private LOrder lOrder;

    //获取订单1
    @PostMapping("/getPendingPayment")
    public Map getPendingPayment(String session_key){
        return lOrder.getPendingPayment(session_key);
    }

    //获取订单2
    @PostMapping("/getPendingPayment2")
    public Map getPendingPayment2(String session_key){
        return lOrder.getPendingPayment2(session_key);
    }


    //购买商品（单个）
    @PostMapping(value = "/buyoneItem")
    public boolean buyoneItem(String item_name,String session_key,String item_id,String address_id,int buynumber,String item_price){
        return lOrder.buyoneItem(item_name,session_key,item_id,address_id,buynumber,item_price);
    }
}
