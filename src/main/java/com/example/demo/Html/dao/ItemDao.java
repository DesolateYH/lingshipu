package com.example.demo.Html.dao;

import com.example.demo.Html.model.Item;
import com.example.demo.Html.model.ItemHomePageMore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/3/28 0:09
 * @description：${description}
 */
public interface ItemDao extends JpaRepository<Item,Integer> {
    Item findAllByItemId(String itemId);
}
