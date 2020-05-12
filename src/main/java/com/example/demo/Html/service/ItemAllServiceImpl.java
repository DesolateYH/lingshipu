package com.example.demo.Html.service;

import com.example.demo.Html.repository.ItemAllDao;
import com.example.demo.Html.domian.po.ItemAllModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 商品总表服务类
 * @author: QWS
 * @create: 2020-03-03 22:28
 */
@Service
public class ItemAllServiceImpl {
    @Autowired
    ItemAllDao itemAllDao;
    @Autowired
    ItemServiceImpl itemService;

    /**
     * 获取所有商品
     *
     * @return
     */
    public List<ItemAllModel> findAllItemAll() {
        return itemAllDao.findAll();
    }

    /**
     * 修改未投放库存
     *
     * @param num     修改数量
     * @param item_id 商品id
     * @return
     */
    public void updateInventoryBalance(int item_id, int num) throws Exception {
        ItemAllModel itemAllModel = itemAllDao.findByItemId(item_id);
        int currentInventoryBalance = itemAllModel.getInventoryBalance();
        if (currentInventoryBalance < -num) {
            throw new Exception("updateInventoryBalance error，事务异常，投放数量超过已有库存");
        } else {
            itemAllModel.setInventoryBalance(currentInventoryBalance + num);
            itemAllDao.save(itemAllModel);
        }

    }

    /**
     * 修改已投放库存
     *
     * @param num 修改数量
     * @return
     */
    public void updateStockCurrentAll(int item_id, int num) throws Exception {
        ItemAllModel itemAllModel = itemAllDao.findByItemId(item_id);
        int currentStockCurrentAll = itemAllModel.getStockCurrentAll();
        itemAllModel.setStockCurrentAll(currentStockCurrentAll + num);
        itemAllDao.save(itemAllModel);
        /*     throw new Exception("updateStockCurrentAll error");*/
    }

    /**
     * 事务，往寝室商品表中添加商品，修改总表中的未投放库存数和已投放库存数
     * @param shop_id 寝室id
     * @param item_id 商品id
     * @param stock_mix 添加库存的数量
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void addItemToShopTransactional(Integer shop_id, Integer item_id, Integer stock_mix) throws Exception {
        itemService.addItemToShop(shop_id, item_id, stock_mix);
        updateInventoryBalance(item_id, -stock_mix);
        updateStockCurrentAll(item_id, stock_mix);
    }


}
