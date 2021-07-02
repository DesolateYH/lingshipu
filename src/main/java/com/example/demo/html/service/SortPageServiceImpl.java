package com.example.demo.html.service;

import com.example.demo.html.domian.po.SortPagePO;
import com.example.demo.html.repository.SortPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: lingshipu
 * @description: 分类
 * @author: QWS
 * @create: 2020-05-12 05:38
 */
@Service
public class SortPageServiceImpl {
    @Autowired
    SortPageRepository sortPageRepository;

    public SortPagePO findBySortId(int sort_id){
        return sortPageRepository.findBySortId(sort_id);
    }
}
