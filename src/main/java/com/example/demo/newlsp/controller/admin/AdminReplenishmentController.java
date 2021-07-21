package com.example.demo.newlsp.controller.admin;

import com.alibaba.fastjson.JSON;
import com.example.demo.html.domian.po.ReplenishmentPO;
import com.example.demo.html.domian.po.ShopInfo;
import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.domian.vo.ReplenishmentVO;
import com.example.demo.html.repository.ItemAllDao;
import com.example.demo.html.repository.ReplenishmentRepository;
import com.example.demo.html.repository.ShopInfoDao;
import com.example.demo.html.service.ItemServiceImpl;
import com.example.demo.html.service.LspVxUserServiceImpl;
import com.example.demo.html.service.UserInfoServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: lingshipu
 * @description: 管理员补货单
 * @author: QWS
 * @create: 2021-07-02 23:54
 */
@RestController
@RequestMapping("/Replenishment")
public class AdminReplenishmentController {
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    ShopInfoDao shopInfoDao;
    @Autowired
    ItemAllDao itemAllDao;
    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    ReplenishmentRepository replenishmentRepository;
    /**
     * 管理员根据商品生成补货订单
     *
     * @param access_token token
     * @param itemJson     json
     * @param shopId       商品id
     * @return 1
     */
    @RequestMapping(value = "/adminCreate")
    public Msg adminCreate(String access_token, String itemJson, Integer shopId) throws JSONException {
        ShopInfo shopInfo = shopInfoDao.findByShopId(shopId);

        JSONObject jsonObject = new JSONObject(itemJson);
        String cart_price_total = jsonObject.getString("cart_price_total");
        JSONArray jsonArray = jsonObject.getJSONArray("cart_list");
        List<ReplenishmentVO> replenishmentVOS = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            ReplenishmentVO replenishmentVO = new ReplenishmentVO();
            replenishmentVO.setReplenishmentItemName(jsonObject2.getString("itemName"));
            replenishmentVO.setReplenishmentItemId(jsonObject2.getInt("itemId"));
            replenishmentVO.setReplenishmentItemNum(jsonObject2.getInt("num"));
            replenishmentVO.setReplenishmenShopAddress(shopInfo.getShopAddress());
            replenishmentVO.setReplenishmentItemPrice(cart_price_total);
            replenishmentVO.setReplenishmentStatusCode(0);
            replenishmentVOS.add(replenishmentVO);
        }

        ReplenishmentPO replenishmentPO = new ReplenishmentPO();
        replenishmentPO.setReplenishmentShopAddress(shopInfo.getShopAddress());
        replenishmentPO.setReplenishmentAllPrice(cart_price_total);
        replenishmentPO.setReplenishmentInfo(JSON.toJSONString(replenishmentVOS));
        replenishmentPO.setReplenishmentStatusCode(0);
        replenishmentRepository.save(replenishmentPO);
        return Msg.statu200().add("info", replenishmentVOS).add("allPrice", cart_price_total);
    }

    /**
     * 管理员获取补货单详情
     *
     * @param access_token token
     * @param replArr      补货单数组 1，2，3，4，5
     * @return 1
     */
    @RequestMapping(value = "/getByArr")
    public Msg getByArr( String access_token, String replArr) {
        String[] arr = replArr.split(",");
        List<ReplenishmentVO> replenishmentVOS = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (String s : arr) {
            map.put(s, replenishmentRepository.findByReplenishmentItemId(Integer.parseInt(s)));
        }
        return Msg.statu200().add("info", map);
    }
    /**
     * 管理员更新补货单
     *
     * @param access_token token
     * @param itemJson     商品json
     * @param replId       补货单id
     * @return 1
     */
    @RequestMapping(value = "/updateForAdmin")
    public Msg updateForAdmin( String access_token, String itemJson, Integer replId) throws JSONException {
        ReplenishmentPO replenishmentPO = replenishmentRepository.findByReplenishmentItemId(replId);
        if (replenishmentPO == null) {
            return Msg.statu403().add("error", "补货单id有误");
        }

        if (replenishmentPO.getReplenishmentStatusCode() != 0) {
            return Msg.statu403().add("error", "订单已锁定");
        }
        ShopInfo shopInfo = shopInfoDao.findByShopAddress(replenishmentPO.getReplenishmentShopAddress());


        JSONObject jsonObject = new JSONObject(itemJson);
        String cart_price_total = jsonObject.getString("cart_price_total");
        JSONArray jsonArray = jsonObject.getJSONArray("cart_list");
        List<ReplenishmentVO> replenishmentVOS = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            ReplenishmentVO replenishmentVO = new ReplenishmentVO();
            replenishmentVO.setReplenishmentItemName(jsonObject2.getString("itemName"));
            replenishmentVO.setReplenishmentItemId(jsonObject2.getInt("itemId"));
            replenishmentVO.setReplenishmentItemNum(jsonObject2.getInt("num"));
            replenishmentVO.setReplenishmenShopAddress(shopInfo.getShopAddress());
            replenishmentVO.setReplenishmentItemPrice(cart_price_total);
            replenishmentVO.setReplenishmentStatusCode(0);
            replenishmentVOS.add(replenishmentVO);
        }


        replenishmentPO.setReplenishmentAllPrice(cart_price_total);
        replenishmentPO.setReplenishmentInfo(JSON.toJSONString(replenishmentVOS));

        replenishmentRepository.save(replenishmentPO);
        return Msg.statu200().add("info", replenishmentVOS).add("allPrice", cart_price_total);
    }
    /**
     * 管理员删除补货单
     * @param access_token token
     * @param replId 补货单id
     * @return 1
     */
    @RequestMapping(value = "/deleteForAdmin")
    public Msg deleteForAdmin( String access_token, Integer replId) {
        ReplenishmentPO replenishmentPO = replenishmentRepository.findByReplenishmentItemId(replId);
        if (replenishmentPO == null) {
            return Msg.statu403().add("error", "补货单id有误");
        }

        if (replenishmentPO.getReplenishmentStatusCode() != 0) {
            return Msg.statu403().add("error", "订单已锁定");
        }
        replenishmentRepository.deleteById(replId);
        return Msg.statu200();
    }

}
