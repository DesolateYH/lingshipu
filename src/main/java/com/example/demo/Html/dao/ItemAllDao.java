package com.example.demo.Html.dao;

import com.example.demo.Html.model.ItemAllModel;
import com.example.demo.Html.model.ItemHomePageMore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: lingshipu
 * @description: 商品总表接口类
 * @author: QWS
 * @create: 2020-03-03 22:27
 */
public interface ItemAllDao extends JpaRepository<ItemAllModel,Integer> {
    /**
     * 寻找商品信息
     * @param i 商品id
     * @return 商品信息
     */
    ItemAllModel findByItemId(int i);
}
