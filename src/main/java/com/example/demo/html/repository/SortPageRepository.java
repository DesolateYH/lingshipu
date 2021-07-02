package com.example.demo.html.repository;

import com.example.demo.html.domian.po.SortPagePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: lingshipu
 * @description: 分类
 * @author: QWS
 * @create: 2020-05-12 05:35
 */
public interface SortPageRepository extends JpaRepository<SortPagePO,Integer> {
    /**
     * 获取分类信息
     * @param i 分类id
     * @return 1
     */
    SortPagePO findBySortId(int i);
}
