package com.example.demo.newlsp.controller.admin;

import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.service.LspVxUserServiceImpl;
import com.example.demo.html.service.UserInfoServiceImpl;
import com.example.demo.newlsp.repository.InventoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: lingshipu
 * @description: 盘货
 * @author: QWS
 * @create: 2021-04-24 22:02
 */

@RestController
@RequestMapping("/ir")
public class InventoryRecordController {
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    InventoryRecordRepository inventoryRecordRepository;


    /**
     * 获取所有盘货订单
     *
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getAll")
    public Msg getAll( String access_token) {
        return Msg.statu200().add("info", inventoryRecordRepository.findAll());
    }

    /**
     * 获取盘货订单
     * @param access_token token
     * @param shopId 寝室id
     * @return 1
     */
    @RequestMapping(value = "/getByShopId")
    public Msg getByShopId( String access_token,Integer shopId) {
        return Msg.statu200().add("info",inventoryRecordRepository.findByInventoryRecordShopId(shopId));
    }
}
