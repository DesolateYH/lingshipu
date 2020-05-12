package com.example.demo.Html.repository;

import com.example.demo.Html.domian.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：Q
 * @date ：Created in 2019/3/28 0:09
 * @description：${description}
 */
public interface ItemDao extends JpaRepository<Item,Integer> {
    Item findAllByItemId(String itemId);
}
