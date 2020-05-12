package com.example.demo.Html.controller;

import com.example.demo.Html.SessionUtil;
import com.example.demo.Html.domian.po.ItemHomePageMore;
import com.example.demo.Html.domian.po.LspOrderInfoPO;
import com.example.demo.Html.domian.po.SortPagePO;
import com.example.demo.Html.domian.vo.Msg;
import com.example.demo.Html.domian.vo.SortPageItemVO;
import com.example.demo.Html.domian.vo.SortPageVO;
import com.example.demo.Html.repository.SortPageRepository;
import com.example.demo.Html.service.ItemServiceImpl;
import com.example.demo.Html.service.LspOrderInfoServiceImpl;
import com.example.demo.Html.service.LspVxUserServiceImpl;
import com.example.demo.Html.service.UserInfoServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: lingshipu
 * @description: 用户控制层
 * @author: QWS
 * @create: 2020-05-11 03:41
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class LspUserController {
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    SortPageRepository sortPageRepository;
    @Autowired
    LspOrderInfoServiceImpl lspOrderInfoService;

    //接受code转换成openid查数据库，有则返回数据库中的课表，没有则不返回
    @RequestMapping(value = "/getCode")
    public Msg getCode(String code, Integer shop_id) {
        System.out.println(code);
        if (code == null || code.length() < 1) {
            return Msg.statu400();
        }
        String[] openidAndToken = lspVxUserService.decodeUserInfo(code).split("\\|");
        String openId = openidAndToken[0];
        String access_token = openidAndToken[1];
        /*  String refresh_token = lspVxUserService.getRefreshToken(access_token);*/
        if (lspVxUserService.CountByOpenid(openId) == 0) {
            if (lspVxUserService.saveSessionKey(openId, access_token, shop_id) == 1) {
                String[] arrary = lspVxUserService.getUserNickNameAndGerder(access_token, openId);
                lspVxUserService.saveUserInfo(openId, shop_id, arrary[0], arrary[1]);

            } else {
                return Msg.statu500();
            }
        } else if (lspVxUserService.CountByOpenid(openId) == 1) {
            if (lspVxUserService.updateSessionKey(openId, access_token) != 1) {
                return Msg.statu500();
            }
        } else {
            Msg.statu500();
        }
        return Msg.statu200().add("userInfo", lspVxUserService.getUserHeadAndNickName(access_token, openId));
    }


    /**
     * 获取寝室商品
     *
     * @param access_token access_token
     * @return 1
     */
    @RequestMapping(value = "/getItem")
    public Msg getItem(String access_token) {
        int shop_id = lspVxUserService.getShopIdByToken(access_token);
        List<ItemHomePageMore> itemHomePageMores = itemService.findByItemInfoByShopId(shop_id);
        List<SortPageVO> sortPageVOS = new ArrayList<>();
        List<SortPagePO> sortPagePOS = sortPageRepository.findAll();
        for (SortPagePO sortPagePO : sortPagePOS) {
            List<SortPageItemVO> sortPageItemVOS = new ArrayList<>();
            for (ItemHomePageMore itemHomePageMore : itemHomePageMores) {
                if (itemHomePageMore.getItemSort() == sortPagePO.getSortId()) {
                    SortPageItemVO sortPageItemVO = new SortPageItemVO();
                    sortPageItemVO.setItemAttribute(0);
                    sortPageItemVO.setItemId(itemHomePageMore.getItemId());
                    sortPageItemVO.setItemPhotoAddress(itemHomePageMore.getItemPicUrl());
                    sortPageItemVO.setItemPrice(Float.parseFloat(itemHomePageMore.getItemPrice()));
                    sortPageItemVO.setItemStockCurrent(itemHomePageMore.getStockCurrent());
                    sortPageItemVO.setItemName(itemHomePageMore.getItemName());
                    sortPageItemVOS.add(sortPageItemVO);
                }
            }
            SortPageVO sortPageVO = new SortPageVO();
            sortPageVO.setSortPhotoAddress(sortPagePO.getSortPhotoAddress());
            sortPageVO.setSortName(sortPagePO.getSortName());
            sortPageVO.setSortId(sortPagePO.getSortId());
            sortPageVO.setSortPageItem(sortPageItemVOS);
            sortPageVOS.add(sortPageVO);
        }
        return Msg.statu200().add("Info", sortPageVOS);
    }

    /**
     * 购买商品
     *
     * @param access_token access_token
     * @param itemJson     商品json
     * @return
     */
    @RequestMapping(value = "/buyItems")
    public Msg buyItem(String access_token, String itemJson) {
        if (access_token == null || itemJson == null || access_token.length() < 1 || itemJson.length() < 1) {
            return Msg.statu400();
        }
        if (lspVxUserService.getByToken(access_token) == null) {
            return Msg.statu401();
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        JSONObject json = JSONObject.fromObject(itemJson);
        float cart_price_total = (float)json.get("allPrice");
        LspOrderInfoPO lspOrderInfoPO = new LspOrderInfoPO();
        lspOrderInfoPO.setOrderItemJson(itemJson);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        lspOrderInfoPO.setOrderTime(df.format(new Date()));

        lspOrderInfoPO.setOrderPrice(cart_price_total);
        lspOrderInfoPO.setVxUserOpenid(openid);

        lspOrderInfoService.saveOrderInfo(lspOrderInfoPO);
        return Msg.statu200();
    }


}
