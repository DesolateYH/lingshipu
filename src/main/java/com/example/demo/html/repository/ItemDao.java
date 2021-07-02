package com.example.demo.html.repository;

import com.example.demo.html.domian.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：Q
 * @date ：Created in 2019/3/28 0:09
 * @description：${description}
 */
public interface ItemDao extends JpaRepository<Item,Integer> {
    Item findAllByItemId(int itemId);
}
