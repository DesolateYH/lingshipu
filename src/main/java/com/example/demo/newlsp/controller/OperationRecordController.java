package com.example.demo.newlsp.controller;

import com.example.demo.CheckParamsInterceptor;
import com.example.demo.html.domian.po.ItemAllModel;
import com.example.demo.html.domian.po.UserInfoModel;
import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.repository.UserInfoDao;
import com.example.demo.html.service.LspVxUserServiceImpl;
import com.example.demo.html.service.UserInfoServiceImpl;
import com.example.demo.newlsp.domain.po.OperationRecordChildItemPO;
import com.example.demo.newlsp.domain.po.OperationRecordPO;
import com.example.demo.newlsp.repository.OperationRecordChildItemRepository;
import com.example.demo.newlsp.repository.OperationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.CheckParamsInterceptor.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.html.ToolUntil.GetGUID;

/**
 * @program: lingshipu
 * @description: 操作记录
 * @author: QWS
 * @create: 2021-02-19 21:46
 */
@RestController
@RequestMapping("/opeartionRecord")
public class OperationRecordController {
    @Autowired
    OperationRecordRepository operationRecordRepository;
    @Autowired
    OperationRecordChildItemRepository operationRecordChildItemRepository;
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    UserInfoDao userInfoDao;

    /**
     * 查询所有操作记录
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getAllOpeartionRecord")
    public Msg getAllOpeartionRecord(@ParamsNotNull String access_token) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        return Msg.statu200().add("info", operationRecordRepository.findAll());
    }

    /**
     * 查询操作物品清单
     *
     * @param operationRecordSerial 操作序列号（不是id）
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getOpeartionRecordDetail")
    public Msg getOpeartionRecordDetail(@ParamsNotNull String access_token,@ParamsNotNull String operationRecordSerial) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        return Msg.statu200().add("info", operationRecordChildItemRepository.findByOperationRecordSerial(operationRecordSerial));
    }


    public void saveOpeartionRecord(String adminId, List<ItemAllModel> itemAllModels, String type) {
        String serial = GetGUID();


        UserInfoModel userInfoModel = userInfoDao.findByAdminId(adminId);
        OperationRecordPO operationRecordPO = new OperationRecordPO();
        operationRecordPO.setOperationRecordAdminId(adminId);
        operationRecordPO.setOperationRecordAdminName(userInfoModel.getUserName());
        operationRecordPO.setOperationRecordType(type);
        operationRecordPO.setOperationRecordSerial(serial);
        operationRecordRepository.save(operationRecordPO);

        List<OperationRecordChildItemPO> operationRecordChildItemPOS = new ArrayList<>();
        for (ItemAllModel allModel : itemAllModels) {
            OperationRecordChildItemPO operationRecordChildItemPO = new OperationRecordChildItemPO();
            operationRecordChildItemPO.setOperationRecordSerial(serial);
            operationRecordChildItemPO.setItemId(allModel.getItemId());
            operationRecordChildItemPO.setItemName(allModel.getItemName());
            operationRecordChildItemPO.setItemPrice(allModel.getItemPrice());
            operationRecordChildItemPO.setInventoryBalance(allModel.getInventoryBalance());
            operationRecordChildItemPO.setStockCurrentAll(allModel.getStockCurrentAll());
            operationRecordChildItemPO.setSalesVolumeAll(allModel.getSalesVolumeAll());
            operationRecordChildItemPO.setItemPicUrl(allModel.getItemPicUrl());
            operationRecordChildItemPOS.add(operationRecordChildItemPO);
        }
        operationRecordChildItemRepository.saveAll(operationRecordChildItemPOS);
    }
}
