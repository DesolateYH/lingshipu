package com.example.demo.newlsp.controller;

import com.example.demo.CheckParamsInterceptor;
import com.example.demo.html.domian.po.ShopInfo;
import com.example.demo.html.domian.po.UserInfoModel;
import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.repository.ShopInfoDao;
import com.example.demo.html.service.LspVxUserServiceImpl;
import com.example.demo.html.service.ShopInfoServiceImpl;
import com.example.demo.html.service.UserInfoServiceImpl;
import com.example.demo.newlsp.domain.po.NoticePO;
import com.example.demo.newlsp.repository.NoticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.CheckParamsInterceptor.*;

import javax.annotation.Resource;

/**
 * @program: lingshipu
 * @description: 公告
 * @author: QWS
 * @create: 2021-03-14 01:19
 */

@RestController
@RequestMapping("/notic")
public class NoticController {
    @Autowired
    NoticRepository noticRepository;
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    ShopInfoServiceImpl shopInfoService;
    @Autowired
    ShopInfoDao shopInfoDao;

    /**
     * 获取公告(全局）
     *
     * @return 1
     */
    @RequestMapping(value = "/getNotic")
    public Msg getNotic() {
        return Msg.statu200().add("info", noticRepository.findAll());
    }

    /**
     * 更新公告（全局）
     *
     * @param access_token token
     * @param notic        新公告(可以为空）
     * @return 1
     */
    @RequestMapping(value = "/saveNotic")
    public Msg saveNotic(@CheckParamsInterceptor.ParamsNotNull String access_token, String notic) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        NoticePO noticePO = noticRepository.findByNoticId(1);
        if (notic == null) {
            noticePO.setNoticInfo("");
        } else {
            noticePO.setNoticInfo(notic);
        }
        try {
            noticRepository.save(noticePO);
            return Msg.statu200();
        } catch (Exception e) {
            return Msg.statu403().add("error", e.getMessage());
        }
    }

    /**
     * 获取寝室公告
     *
     * @param access_token token
     * @param shopId       寝室id
     * @return 1
     */
    @RequestMapping(value = "/getNoticByShopId")
    public Msg getNoticByShopId(@ParamsNotNull String access_token, @ParamsNotNull String shopId) {
        if("0".equals(shopId)){
            return Msg.statu200().add("info",shopInfoDao.findAll());
        }
        ShopInfo shopInfo = shopInfoService.findShopInfoByShopId(Integer.parseInt(shopId));
        if (shopInfo == null) {
            return Msg.statu403().add("error", "寝室号不存在");
        }
        return Msg.statu200().add("info", shopInfo.getShopNotic());
    }


    /**
     * 更新寝室公告
     *
     * @param access_token token
     * @param notic        公告内容(可以为空）
     * @param shopId       寝室id
     * @return 1
     */
    @RequestMapping(value = "/saveNoticByShopId")
    public Msg saveNoticByShopId(@CheckParamsInterceptor.ParamsNotNull String access_token,
                                 String notic, @ParamsNotNull String shopId) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        ShopInfo shopInfo = shopInfoService.findShopInfoByShopId(Integer.parseInt(shopId));
        if (shopInfo == null) {
            return Msg.statu403().add("error", "寝室号不存在");
        }
        if (notic == null) {
            shopInfo.setShopNotic("");
        } else {
            shopInfo.setShopNotic(notic);
        }

        try {
            shopInfoDao.save(shopInfo);
            return Msg.statu200();
        } catch (Exception e) {
            return Msg.statu403().add("error", e.getMessage());
        }
    }

}
