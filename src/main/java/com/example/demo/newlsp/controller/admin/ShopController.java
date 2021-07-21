package com.example.demo.newlsp.controller.admin;

import com.example.demo.html.domian.po.ShopInfo;
import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.repository.*;
import com.example.demo.newlsp.repository.InventoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: lingshipu
 * @description: 盒子
 * @author: QWS
 * @create: 2021-05-09 01:57
 */

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    ItemHomePageMoreDao itemHomePageMoreDao;
    @Autowired
    LspOrderInfoRepository orderInfoRepository;
    @Autowired
    InventoryRecordRepository inventoryRecordRepository;
    @Autowired
    ReplenishmentRepository replenishmentRepository;
    @Autowired
    ShopInfoDao shopInfoDao;

    /**
     * 获取所有商品，历史订单，盘货记录，补货记录
     * @param access_token token
     * @param shop_id 寝室id
     * @return 1
     */
    @RequestMapping(value = "/getAll")
    public Msg getAll(  String access_token, Integer shop_id){
        ShopInfo shopInfo = shopInfoDao.findByShopId(shop_id);
        if(shopInfo==null){
            return Msg.statu403().add("error","寝室id不存在");
        }
        return Msg.statu200().add("userInfo",userInfoDao.findByShopId(shop_id))
                .add("HisOrders",orderInfoRepository.findByShopAddress(shopInfo.getShopAddress()))
                .add("InvRecord",inventoryRecordRepository.findByInventoryRecordShopId(shop_id))
                .add("RepRecord",replenishmentRepository.findByReplenishmentShopAddress(shopInfo.getShopAddress()))
                .add("item",itemHomePageMoreDao.findByParentShopId(shop_id))
                .add("notic",shopInfo.getShopNotic());
    }


}
