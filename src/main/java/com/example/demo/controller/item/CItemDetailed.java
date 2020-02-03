package com.example.demo.controller.item;

import com.example.demo.Logic.item.LItemDetailed;;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class CItemDetailed {

    //查询物品详情
    @PostMapping(value = "/homepage_more_getItem")
    public Map<String, Object> getItemDetailed(String item_id) throws Exception {
        LItemDetailed lItemDetailed = new LItemDetailed();
        return lItemDetailed.Litemdetailed(item_id);
    }


}

