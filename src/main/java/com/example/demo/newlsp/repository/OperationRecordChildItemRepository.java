package com.example.demo.newlsp.repository;

import com.example.demo.newlsp.domain.po.OperationRecordChildItemPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: lingshipu
 * @description:
 * @author: QWS
 * @create: 2021-02-19 22:40
 */
public interface OperationRecordChildItemRepository extends JpaRepository<OperationRecordChildItemPO,Integer> {
    /**
     * 查询记录
     * @param operationRecordSerial 序列号
     * @return 1
     */
    List<OperationRecordChildItemPO> findByOperationRecordSerial(String operationRecordSerial);
}
