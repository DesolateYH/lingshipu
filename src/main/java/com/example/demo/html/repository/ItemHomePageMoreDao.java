package com.example.demo.html.repository;

import com.example.demo.html.domian.po.ItemHomePageMore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/4/4 11:06
 * @description：寝室商品
 */
public interface ItemHomePageMoreDao extends JpaRepository<ItemHomePageMore,Integer> {
    ItemHomePageMore findAllByItemId(int itemId);

    /**
     * 获取商品信息
     * @param itemId 商品id
     * @return 1
     */
    List<ItemHomePageMore> findByItemId(int itemId);

    /**
     * 获取商品信息
     * @param shopId 寝室id
     * @return 所有商品信息
     */
    List<ItemHomePageMore> findByParentShopId(int shopId);

    /**
     * 删除相应商品
     * @param i 寝室id
     */
    void deleteByParentShopId(int i);

    /**
     * 获取商品信息
     * @param i 商品id,寝室id
     * @return 商品信息对象
     */
    ItemHomePageMore findByItemIdAndParentShopId(int i,int p);

    /**
     * 删除寝室内的商品
     * @param s 寝室id
     * @param i 商品id
     */
    @Modifying
    @Transactional
    @Query(value = "delete from vx_homepage_more  where parent_shop_id = ?1 and item_id=?2",nativeQuery = true)
    int deleteByParentShopIdAndItemId(int s,int i);
    /*    void deleteByParentShopIdAndItemId(int s,int i);*/
}
