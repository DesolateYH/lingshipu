package com.example.demo.newlsp.repository;


import com.example.demo.newlsp.domain.po.DeliveryVehiclePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 配送车
 * @author: QWS
 * @create: 2021-04-15 20:56
 */
public interface DeliveryVehicleRepository extends JpaRepository<DeliveryVehiclePO,Integer> {
    List<DeliveryVehiclePO> findByAdminId(String adminId);
    DeliveryVehiclePO findByAdminIdAndDelieveryVehicleId(String adminId, int delieveryVehicleId);
}
