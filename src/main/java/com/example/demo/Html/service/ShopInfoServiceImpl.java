package com.example.demo.Html.service;

import com.example.demo.Html.repository.ShopInfoDao;
import com.example.demo.Html.domian.po.ShopInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 店铺信息服务类
 * @author: QWS
 * @create: 2020-03-01 19:54
 */
@Service
public class ShopInfoServiceImpl {
    final
    ShopInfoDao shopInfoDao;

    public ShopInfoServiceImpl(ShopInfoDao shopInfoDao) {
        this.shopInfoDao = shopInfoDao;
    }


    /**
     * 获取所有寝室信息
     */
    public List<ShopInfo> findAllShopInfo() {
        return shopInfoDao.findAll();
    }

    /**
     * 获取寝室信息
     *
     * @param shop_id 寝室号
     * @return 寝室信息
     */
    public ShopInfo findShopInfoByShopId(int shop_id) {
        return shopInfoDao.findByShopId(shop_id);
    }

    /**
     * 寝室号是否已经存在
     *
     * @param shopAddress 寝室地址
     * @return 是、否
     */
    public boolean findExistShopByShopAddress(String shopAddress) {
        return !(shopInfoDao.findByShopAddress(shopAddress) == null);
    }

    /**
     * 删除寝室信息
     *
     * @param shopId 寝室id
     * @return 是否删除成功
     */
    public boolean deleteShopInfoByShopId(int shopId) {
        try {
            return shopInfoDao.deleteByShopId(shopId) == 1;
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return false;
        }
    }

}
