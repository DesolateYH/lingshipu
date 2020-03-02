package com.example.demo.controller.Community;

import com.example.demo.Logic.item.LItemDetailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: lingshipu
 * @description: 管理员类
 * @author: QWS
 * @create: 2020-02-07 16:55
 */
@RequestMapping(value = "admin")
@RestController
@CrossOrigin
public class CAdmin {
    @Autowired
    LItemDetailed lItemDetailed;
    /**
     * 删除商品
     * @param item_id 商品id
     * @return true/false
     */
    @RequestMapping(value = "/deleteItem")
    public boolean adminDeleteItem(String item_id){
        if(item_id == null){
            return false;
        }
        int flag = lItemDetailed.deleteItemByItemId(Integer.parseInt(item_id));
        return flag > 0;
    }
}
