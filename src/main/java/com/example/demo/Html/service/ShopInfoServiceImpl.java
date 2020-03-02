package com.example.demo.Html.service;

import com.example.demo.Html.dao.ShopInfoDao;
import com.example.demo.Html.model.ShopInfo;
import org.springframework.beans.factory.annotation.Autowired;
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

}
