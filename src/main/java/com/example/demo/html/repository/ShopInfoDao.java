package com.example.demo.html.repository;

import com.example.demo.html.domian.po.ShopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: lingshipu
 * @description: 店铺信息接口层
 * @author: QWS
 * @create: 2020-03-01 19:52
 */

public interface ShopInfoDao extends JpaRepository<ShopInfo,Integer> {
    /**
     * 获取店铺信息
     * @param shopId 店铺id
     * @return 店铺信息
     */
    ShopInfo findByShopId(int shopId);



    /**
     * 获取相应寝室信息数量
     * @param shopAddress 寝室地址
     * @return 找到的记录条数
     */
    ShopInfo findByShopAddress(String shopAddress);


    /**
     * 删除寝室信息
     * @param s 寝室id
     */
    @Modifying
    @Transactional
    @Query(value = "delete from shop_info  where shop_id = ?1",nativeQuery = true)
    int deleteByShopId(int s);
/*    void deleteByShopId(int shopId);*/




}
