package com.example.demo.controller.item;

import com.example.demo.Logic.item.LItemSort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class CItemSort {

    //物品排序
    @RequestMapping(value = "/Sort_Commodities",method = RequestMethod.POST)
    public Map<String, Object> Sort_Commodities(String method) {
        LItemSort lItemSort = new LItemSort();
        return lItemSort.LSort_Commodities(method);
    }


}
