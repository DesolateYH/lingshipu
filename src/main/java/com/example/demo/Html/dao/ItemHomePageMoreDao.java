package com.example.demo.Html.dao;

import com.example.demo.Html.model.Item;
import com.example.demo.Html.model.ItemHomePageMore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/4/4 11:06
 * @description：${description}
 */
public interface ItemHomePageMoreDao extends JpaRepository<ItemHomePageMore,Integer> {
    ItemHomePageMore findAllByItemId(int itemId);

    /**
     * 获取商品信息
     * @param shopId 寝室id
     * @return 所有商品信息
     */
    List<ItemHomePageMore> findByParentShopId(int shopId);

}
