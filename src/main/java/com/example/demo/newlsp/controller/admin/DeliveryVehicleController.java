package com.example.demo.newlsp.controller.admin;

import com.example.demo.html.domian.po.ItemAllModel;
import com.example.demo.html.domian.po.ReplenishmentPO;
import com.example.demo.html.domian.po.UserInfoModel;
import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.repository.ReplenishmentRepository;
import com.example.demo.html.service.LspVxUserServiceImpl;
import com.example.demo.html.service.UserInfoServiceImpl;
import com.example.demo.newlsp.OthersServiceImpl;
import com.example.demo.newlsp.domain.po.DeliveryVehiclePO;
import com.example.demo.newlsp.domain.vo.DeliveryVehicleVO;
import com.example.demo.newlsp.repository.DeliveryVehicleRepository;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: lingshipu
 * @description: 配送车
 * @author: QWS
 * @create: 2021-04-15 21:00
 */
@RestController
@RequestMapping("/deliveryVehicle")
public class DeliveryVehicleController {
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    DeliveryVehicleRepository deliveryVehicleRepository;
    @Autowired
    ReplenishmentRepository replenishmentRepository;
    @Autowired
    OthersServiceImpl othersService;
    @Autowired
    OperationRecordController operationRecordController;

    /**
     * 根据创建者获取配送车
     *
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getByAdminId")
    public Msg getByAdminId( String access_token) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        List<ItemAllModel> itemAllModels = new ArrayList<>();
        operationRecordController.saveOpeartionRecord(userInfoModel.getAdminId(), itemAllModels, "创建配送车");
        return Msg.statu200().add("info", deliveryVehicleRepository.findByAdminId(userInfoModel.getAdminId()));
    }

    /**
     * 完成配送车
     *
     * @param access_token      token
     * @param deliveryVehicleId 配送车id
     * @return 1
     */
    @RequestMapping(value = "/finishDeliveryVehicle")
    public Msg finishDeliveryVehicle( String access_token,  Integer deliveryVehicleId) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        DeliveryVehiclePO deliveryVehiclePO = deliveryVehicleRepository.findByAdminIdAndDelieveryVehicleId
                (userInfoModel.getAdminId(), deliveryVehicleId);
        if (deliveryVehiclePO == null) {
            return Msg.statu403().add("error", "信息有误");
        }

        String[] replenishmentIdArr = deliveryVehiclePO.getDelieveryVehicleReplenishmentId().split("\\|");
        int flag = 0;
        StringBuilder replenishmentIdStr = new StringBuilder("");
        for (String s : replenishmentIdArr) {
            if (!othersService.updateReplenishmentStatusCode(Integer.valueOf(s), 2)) {
                replenishmentIdStr.append(s).append("、");
                flag = 1;
            }
        }
        deliveryVehiclePO.setDelieveryVehicleisFinish(1);
        deliveryVehicleRepository.save(deliveryVehiclePO);
        if (flag == 0) {
            List<ItemAllModel> itemAllModels = new ArrayList<>();
            operationRecordController.saveOpeartionRecord(userInfoModel.getAdminId(), itemAllModels, "完成配送车：" + deliveryVehicleId);
            return Msg.statu200();
        } else {
            return Msg.statu403().add("error", "以下订单号不存在,已存在补货订单已经完成操作：" + replenishmentIdStr);
        }

    }

    /**
     * 获取所有配送车(需要权限）
     *
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getAllByAdminId")
    public Msg getAllByAdminId( String access_token) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        return Msg.statu200().add("info", deliveryVehicleRepository.findAll());
    }

    /**
     * 创建配送车
     *
     * @param access_token token
     * @param replementId  补货订单id(1，2，3，4，5，……)
     * @return 1
     */
    @RequestMapping(value = "/saveDeliveryVehicle")
    @Transactional(rollbackOn = Exception.class)
    public Msg saveDeliveryVehicle(String access_token, String replementId) throws Exception {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }

        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        String[] replementIdArr = replementId.split(",");

        List<DeliveryVehicleVO> deliveryVehicleVOS = new ArrayList<>();
        for (String s : replementIdArr) {
            ReplenishmentPO replenishmentPO = replenishmentRepository.findByReplenishmentItemId(Integer.parseInt(s));

            if (replenishmentPO.getReplenishmentIsLock() == 1) {
                throw new Exception("补货单[" + replenishmentPO.getReplenishmentItemId() + "]已被锁定");
            }
            if (replenishmentPO.getIsFinish() == 1) {
                throw new Exception("补货单[" + replenishmentPO.getReplenishmentItemId() + "]已完成");
            }
            String replenishmentJson = replenishmentPO.getReplenishmentInfo();
            JSONArray jsonarray = JSONArray.fromObject(replenishmentJson);

            for (int j = 0; j < jsonarray.size(); j++) {
                int itemId = jsonarray.getJSONObject(j).getInt("replenishmentItemId");
                String itemName = jsonarray.getJSONObject(j).getString("replenishmentItemName");
                int itemNum = jsonarray.getJSONObject(j).getInt("replenishmentItemNum");

                int flag = 0;
                for (DeliveryVehicleVO deliveryVehicleVO : deliveryVehicleVOS) {
                    if (deliveryVehicleVO.getItemId() == itemId) {
                        int num = deliveryVehicleVO.getItemNum();
                        deliveryVehicleVO.setItemNum(num + 1);
                        deliveryVehicleVOS.set(j, deliveryVehicleVO);
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    DeliveryVehicleVO deliveryVehicleVO = new DeliveryVehicleVO();
                    deliveryVehicleVO.setItemId(itemId);
                    deliveryVehicleVO.setItemName(itemName);
                    deliveryVehicleVO.setItemNum(itemNum);
                    deliveryVehicleVOS.add(deliveryVehicleVO);
                }
            }
            replenishmentPO.setReplenishmentIsLock(1);
            replenishmentRepository.save(replenishmentPO);
        }
//            List list = (List) JSONArray.toCollection(jsonarray, ItemAllModel.class);
//            Iterator it = list.iterator();
//
//            while (it.hasNext()) {
//                ItemAllModel p = (ItemAllModel) it.next();
//                DeliveryVehicleVO deliveryVehicleVO = new DeliveryVehicleVO();
//                deliveryVehicleVO.setItemId(p.getItemId());
//                deliveryVehicleVO.setItemName(p.getItemName());
//                deliveryVehicleVO.setItemPicUrl(p.getItemPicUrl());
//                deliveryVehicleVOS.add(deliveryVehicleVO);
//
//                int flag = 0;
//                for (int j = 0; j < deliveryVehicleVOS.size(); j++) {
//                    if (deliveryVehicleVOS.get(j).getItemId() == p.getItemId()) {
//                        DeliveryVehicleVO deliveryVehicleVO1 = new DeliveryVehicleVO();
//                        deliveryVehicleVO1.setItemId(deliveryVehicleVOS.get(j).getItemId());
//                        deliveryVehicleVO1.setItemName(deliveryVehicleVOS.get(j).getItemName());
//                        deliveryVehicleVO1.setItemNum(deliveryVehicleVOS.get(j).getItemNum() + 1);
//                        deliveryVehicleVO1.setItemPicUrl(deliveryVehicleVOS.get(j).getItemPicUrl());
//                        deliveryVehicleVOS.set(j, deliveryVehicleVO1);
//                        flag = 1;
//                    }
//                }
//                if (flag == 0) {
//                    DeliveryVehicleVO deliveryVehicleVO1 = new DeliveryVehicleVO();
//                    deliveryVehicleVO1.setItemId(p.getItemId());
//                    deliveryVehicleVO1.setItemName(p.getItemName());
//                    deliveryVehicleVO1.setItemNum(1);
//                    deliveryVehicleVO1.setItemPicUrl(p.getItemPicUrl());
//                    deliveryVehicleVOS.add(deliveryVehicleVO1);
//                }
//            }

        DeliveryVehiclePO deliveryVehiclePO = new DeliveryVehiclePO();
        deliveryVehiclePO.setAdminId(userInfoModel.getAdminId());
        deliveryVehiclePO.setDelieveryVehicleReplenishmentId(replementId);
        JSONArray json = JSONArray.fromObject(deliveryVehicleVOS);
        deliveryVehiclePO.setDelieveryVehicleJson(String.valueOf(json));
        deliveryVehiclePO.setDelieveryVehicleisFinish(0);
        List<ItemAllModel> itemAllModels = new ArrayList<>();
        deliveryVehicleRepository.save(deliveryVehiclePO);
        operationRecordController.saveOpeartionRecord(userInfoModel.getAdminId(), itemAllModels, "创建配送车");
        return Msg.statu200();

    }

    /**
     * 删除配送车
     *
     * @param access_token token
     * @param id           id
     * @return 1
     */
    @RequestMapping(value = "/delete")
    @Transactional(rollbackOn = Exception.class)
    public Msg deleteById(String access_token, Integer id) throws Exception {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }

        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        DeliveryVehiclePO deliveryVehiclePO =
                deliveryVehicleRepository.findByAdminIdAndDelieveryVehicleId(userInfoModel.getAdminId(), id);
        if (deliveryVehiclePO == null) {
            return Msg.statu403().add("error", "没有查询到信息");
        }
        String[] replenishmentIdArr = deliveryVehiclePO.getDelieveryVehicleReplenishmentId().split(",");
        for (String s : replenishmentIdArr) {
            ReplenishmentPO replenishmentPO = replenishmentRepository.findByReplenishmentItemId(Integer.parseInt(s));
            replenishmentPO.setReplenishmentIsLock(0);
            replenishmentRepository.save(replenishmentPO);
        }
        replenishmentRepository.deleteById(id);
        return Msg.statu200();
    }

}