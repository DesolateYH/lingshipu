package com.example.demo.Html.service;

import com.example.demo.Html.dao.ItemAllDao;
import com.example.demo.Html.dao.ItemDao;
import com.example.demo.Html.dao.ItemHomePageMoreDao;
import com.example.demo.Html.model.Item;
import com.example.demo.Html.model.ItemAllModel;
import com.example.demo.Html.model.ItemAndHomePageMoreRelation;
import com.example.demo.Html.model.ItemHomePageMore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    ItemAllDao itemAllDao;
    @Autowired
    ItemDao itemDao;
    @Autowired
    BusinessServiceImpl businessService;
    @Autowired
    ItemHomePageMoreDao itemHomePageMoreDao;

    public List getItemAndHomePageMoreRelation() {
        List<ItemAndHomePageMoreRelation> list = new ArrayList<>();
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

    /**
     * 获取店铺内商品列表
     *
     * @param shop_id 店铺id
     * @return 商品列表
     */
    public List<ItemHomePageMore> findByItemInfoByShopId(int shop_id) {
        return itemHomePageMoreDao.findByParentShopId(shop_id);
    }

    /**
     * 删除寝室
     *
     * @param shop_id 寝室id
     * @return
     */
    public boolean deleteItemByShopId(int shop_id) {
        try {
            itemHomePageMoreDao.deleteByParentShopId(shop_id);
            return true;
        } catch (Exception e) {
            System.out.println("Error:" + e);
            return false;
        }
    }

    /**
     * 往寝室内添加商品
     *
     * @param shop_id   寝室id
     * @param item_id   商品id
     * @param stock_mix 投入数量（最大库存）
     */
    public void addItemToShop(int shop_id, int item_id, int stock_mix) throws Exception {
        ItemHomePageMore itemHomePageMore = new ItemHomePageMore();
        ItemAllModel itemAllModel = itemAllDao.findByItemId(item_id);
        itemHomePageMore.setItemId(item_id);
        itemHomePageMore.setItemName(itemAllModel.getItemName());
        itemHomePageMore.setItemPicUrl(itemAllModel.getItemPicUrl());
        itemHomePageMore.setItemPrice(itemAllModel.getItemPrice());
        itemHomePageMore.setStockMix(stock_mix);
        itemHomePageMore.setStockCurrent(stock_mix);
        itemHomePageMore.setParentShopId(shop_id);
        itemHomePageMoreDao.save(itemHomePageMore);
        /*      throw new Exception("addItemToShop error");*/
    }


    /**
     * 删除寝室内的商品
     * @param shop_id 寝室id
     * @param item_id 商品id
     * @return
     */
    @Transactional
    public boolean deleteItemFromShop(int shop_id, int item_id)  {
        try {
            return itemHomePageMoreDao.deleteByParentShopIdAndItemId(item_id, shop_id) == 1;
        }catch (Exception e){
            return false;
        }

    }
}

