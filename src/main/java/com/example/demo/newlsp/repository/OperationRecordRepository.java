package com.example.demo.newlsp.repository;

import com.example.demo.newlsp.domain.po.OperationRecordPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: lingshipu
 * @description: 操作记录
 * @author: QWS
 * @create: 2021-02-19 21:52
 */
public interface OperationRecordRepository extends JpaRepository<OperationRecordPO,Integer> {
}
