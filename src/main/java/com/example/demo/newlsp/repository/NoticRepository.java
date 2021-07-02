package com.example.demo.newlsp.repository;

import com.example.demo.newlsp.domain.po.NoticePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: lingshipu
 * @description: 公告
 * @author: QWS
 * @create: 2021-03-14 01:19
 */
public interface NoticRepository extends JpaRepository<NoticePO,Integer> {
    /**
     * 获取公告
     * @param noticId 公告id
     * @return 1
     */
    NoticePO findByNoticId(int noticId);
}
