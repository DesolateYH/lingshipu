package com.example.demo.newlsp.repository;

import com.example.demo.newlsp.domain.po.InventoryRecordPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 盘货
 * @author: QWS
 * @create: 2021-04-24 21:59
 */
public interface InventoryRecordRepository extends JpaRepository<InventoryRecordPO,Integer> {
    /**
     * 查询盘货记录
     * @param inventoryRecordShopId 寝室id
     * @return 1
     */
    List<InventoryRecordPO> findByInventoryRecordShopId(int inventoryRecordShopId);
}
