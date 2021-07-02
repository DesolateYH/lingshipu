package com.example.demo.html.repository;

import com.example.demo.html.domian.po.ItemAllModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 商品总表接口类
 * @author: QWS
 * @create: 2020-03-03 22:27
 */
public interface
ItemAllDao extends JpaRepository<ItemAllModel, Integer> {
    /**
     * 寻找商品信息
     *
     * @param i 商品id
     * @return 商品信息
     */
    ItemAllModel findByItemId(int i);

    @Query(value = "SELECT * FROM `item_all` limit ?1,?2",nativeQuery = true)
    List<ItemAllModel> findAllPage(int index, int num);

    /**
     * 删除商品
     *
     * @param itemId 商品id
     * @return 1
     */
    int deleteByItemId(int itemId);
}
