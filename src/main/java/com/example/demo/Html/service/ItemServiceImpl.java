package com.example.demo.Html.service;

import com.example.demo.Html.dao.ItemDao;
import com.example.demo.Html.dao.ItemHomePageMoreDao;
import com.example.demo.Html.model.Item;
import com.example.demo.Html.model.ItemAndHomePageMoreRelation;
import com.example.demo.Html.model.ItemHomePageMore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/4/1 19:41
 * @description：${description}
 */

@Service
public class ItemServiceImpl {
    @Autowired
    ItemDao itemDao;
    @Autowired
    BusinessServiceImpl businessService;
    @Autowired
    ItemHomePageMoreDao itemHomePageMoreDao;

    public List getItemAndHomePageMoreRelation() {
        List<ItemAndHomePageMoreRelation>list = new ArrayList<>();
        for (int i = 1; i <= businessService.seachItemNumber(); i++) {
            String ii = String.valueOf(i);
            ItemAndHomePageMoreRelation itemAndHomePageMoreRelation = new ItemAndHomePageMoreRelation();
            Item item = itemDao.findAllByItemId(ii);
            ItemHomePageMore itemHomePageMore = itemHomePageMoreDao.findAllByItemId(i);
            itemAndHomePageMoreRelation.setShengchangriqi(item.getShengchangriqi());
            itemAndHomePageMoreRelation.setItemId(String.valueOf(item.getItemId()));
            itemAndHomePageMoreRelation.setBaozhiqi(item.getBaozhiqi());
            itemAndHomePageMoreRelation.setItemName(itemHomePageMore.getItemName());
            itemAndHomePageMoreRelation.setItemPrice(itemHomePageMore.getItemPrice());
            list.add(itemAndHomePageMoreRelation);
        }
        return list;
    }
}

