package com.example.demo.html.service;

import com.example.demo.html.repository.ReplenishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: lingshipu
 * @description: 补货
 * @author: QWS
 * @create: 2020-06-03 14:37
 */
@Service
public class ReplenishmentServiceImpl {
    @Autowired
    ReplenishmentRepository replenishmentRepository;

}
