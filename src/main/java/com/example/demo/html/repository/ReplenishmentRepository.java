package com.example.demo.html.repository;

import com.example.demo.html.domian.po.ReplenishmentPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 补货
 * @author: QWS
 * @create: 2020-06-03 14:35
 */
public interface ReplenishmentRepository extends JpaRepository<ReplenishmentPO,Integer> {
    /**
     * 查询补货订单信息
     * @param replenishmentShopAddress 寝室地址
     * @return  1
     */
   List<ReplenishmentPO> findByReplenishmentShopAddress(String replenishmentShopAddress);

    /**
     * 查询补货订单信息
     * @param replenishmentItemId 补货id
     * @return 1
     */
   ReplenishmentPO findByReplenishmentItemId(int replenishmentItemId);

    /**
     * 查询补货订单信息
     * @param replenishmentItemId 订单id
     * @param replenishmentShopAddress 寝室地址
     * @return 1
     */
   ReplenishmentPO findByReplenishmentItemIdAndReplenishmentShopAddress(int replenishmentItemId, String replenishmentShopAddress);
}
