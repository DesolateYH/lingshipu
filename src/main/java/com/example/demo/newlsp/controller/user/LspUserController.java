package com.example.demo.newlsp.controller.user;

import com.alibaba.fastjson.JSON;

import com.example.demo.html.controller.AuthUtil;
import com.example.demo.html.controller.HttpRequest;
import com.example.demo.html.controller.WXPayUtil;
import com.example.demo.html.domian.po.*;
import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.domian.vo.ReplenishmentVO;
import com.example.demo.html.domian.vo.SortPageItemVO;
import com.example.demo.html.domian.vo.SortPageVO;
import com.example.demo.html.repository.*;
import com.example.demo.html.service.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    ShopInfoServiceImpl shopInfoService;
    @Autowired
    ShopInfoDao shopInfoDao;
    @Autowired
    ReplenishmentRepository replenishmentRepository;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    ItemAllDao itemAllDao;

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
        String adminId = userInfoService.findByOpenid(openId).getAdminId();
        String permission;
        if ("0".equals(adminId)) {
            permission = "0";
        } else {
            permission = userInfoService.findByUserId(adminId).getPermissions();
        }
        String userAddress = userInfoService.findByOpenid(openId).getUserAddress();
        String shopAddress = shopInfoService.findShopInfoByShopId(shop_id).getShopAddress();
        String creditScore = String.valueOf(shopInfoService.findShopInfoByShopId(shop_id).getShopCreditScore());
        return Msg.statu200().add("userInfo", lspVxUserService.getUserHeadAndNickName(access_token, openId))
                .add("userAddress", userAddress).add("shopAddress", shopAddress).add("permission", permission).add("creditScore", creditScore);
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
     * @return 1
     */
    @RequestMapping(value = "/buyItems")
    public Map<String, String> buyItem(String access_token, String itemJson, HttpServletRequest request, String shop_id) {
        Map<String, String> map = new HashMap<>();
        if (access_token == null || itemJson == null || access_token.length() < 1 || itemJson.length() < 1
                || shop_id == null || shop_id.length() < 1) {
            map.put("Msg", "参数有误");
            return map;
        }
        if (lspVxUserService.getByToken(access_token) == null) {

            map.put("Msg", "参数有误");
            return map;
        }
        String openId = lspVxUserService.getOpenidByToken(access_token);
        JSONObject json = JSONObject.fromObject(itemJson);
        String cart_price_total1 = json.get("cart_price_total").toString();
        if ("".equals(cart_price_total1)) {
            map.put("Msg", "参数有误");
            return map;
        }
        float cart_price_total = Float.parseFloat(cart_price_total1);//总价
        String userAddress = userInfoService.findByOpenid(openId).getUserAddress();
        String shopAddress = shopInfoService.findShopInfoByShopId(Integer.parseInt(shop_id)).getShopAddress();

        try {


            // 拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<String, String>();
            // 获取请求ip地址
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip.contains(",")) {
                String[] ips = ip.split(",");
                ip = ips[0].trim();
            }

            paraMap.put("appid", AuthUtil.APPID); // 商家平台ID
            paraMap.put("body", "寝室零食盒子 " + "\n" + "盒子地址:" + shopAddress + "\n" + "付款人地址:" + userAddress); // 商家名称-销售商品类目、String(128)
            paraMap.put("mch_id", AuthUtil.MCHID); // 商户ID
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr()); // UUID
            paraMap.put("openid", openId);

            String out_trade_no = UUID.randomUUID().toString().replaceAll("-", "");
            paraMap.put("out_trade_no", out_trade_no);// 订单号,每次都不同

            paraMap.put("spbill_create_ip", ip);

            String total_fee = String.valueOf(cart_price_total * 100);
            System.out.println("total_fee" + total_fee);
            paraMap.put("total_fee", total_fee.substring(0, total_fee.length() - 2)); // 支付金额，单位分

            paraMap.put("trade_type", "JSAPI"); // 支付类型
            paraMap.put("notify_url", "http://lsp.chinaqwe.top:10001/pay/notify");// 此路径是微信服务器调用支付结果通知路径随意写
            String sign = WXPayUtil.generateSignature(paraMap, AuthUtil.PATERNERKEY);
            paraMap.put("sign", sign);
            String xml = WXPayUtil.mapToXml(paraMap);// 将所有参数(map)转xml格式

            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
            String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            System.out.println("xml为：" + xml);

            // String xmlStr = HttpRequest.sendPost(unifiedorder_url,
            // xml);//发送post请求"统一下单接口"返回预支付id:prepay_id

            String xmlStr = HttpRequest.httpsRequest(unifiedorder_url, "POST", xml);

            System.out.println("xmlStr为：" + xmlStr);

            // 以下内容是返回前端页面的json数据
            String prepay_id = "";// 预支付id
            if (xmlStr.contains("SUCCESS")) {
                Map<String, String> map1 = WXPayUtil.xmlToMap(xmlStr);
                prepay_id = (String) map1.get("prepay_id");
            }

            LspOrderInfoPO lspOrderInfoPO = new LspOrderInfoPO();
            lspOrderInfoPO.setOrderItemJson(itemJson);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            lspOrderInfoPO.setOrderTime(df.format(new Date()));
            lspOrderInfoPO.setOrderState(0);
            lspOrderInfoPO.setVxUserOpenid(openId);
            lspOrderInfoPO.setOrderOutTradeNo(out_trade_no);
            lspOrderInfoPO.setShopAddress(shopAddress);
            lspOrderInfoPO.setUserAddress(userAddress);
            lspOrderInfoService.saveOrderInfo(lspOrderInfoPO);


            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("appId", AuthUtil.APPID);
            payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(payMap, AuthUtil.PATERNERKEY);
            payMap.put("paySign", paySign);
            //将这个6个参数传给前端
            System.out.println("前端" + payMap);
            return payMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取订单（已经支付）
     *
     * @param access_token access_token
     * @return 1
     */
    @RequestMapping(value = "/getOrder")
    public Msg getOrder(String access_token) {
        if (access_token == null || access_token.length() < 1) {
            return Msg.statu400();
        }
        if (lspVxUserService.getByToken(access_token) == null) {
            return Msg.statu401();
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        lspOrderInfoService.deleteByOrderState(0);
        return Msg.statu200().add("OrderInfo", lspOrderInfoService.getOrderInfo(openid));
    }


    /**
     * 生成补货订单(自动补满）
     *
     * @param access_token toekn
     * @return 1
     */
    @RequestMapping(value = "/replenishment")
    public Msg replenishment(String access_token) {
        if (access_token == null || access_token.length() < 1) {
            return Msg.statu400();
        }
        if (lspVxUserService.getByToken(access_token) == null) {
            return Msg.statu401();
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        String userAddress = userInfoService.findByOpenid(openid).getUserAddress();
        int shopId = shopInfoDao.findByShopAddress(userAddress).getShopId();
        List<ItemHomePageMore> itemHomePageMores = itemService.findByItemInfoByShopId(shopId);
        List<ReplenishmentVO> replenishmentVOS = new ArrayList<>();
        float allPrice = 0;
        for (ItemHomePageMore itemHomePageMore : itemHomePageMores) {
            int stockCurrent = itemHomePageMore.getStockCurrent();
            int stockMix = itemHomePageMore.getStockMix();
            if (stockCurrent < stockMix) {
                ReplenishmentVO replenishmentVO = new ReplenishmentVO();
                replenishmentVO.setReplenishmentItemName(itemHomePageMore.getItemName());
                replenishmentVO.setReplenishmentItemId(itemHomePageMore.getItemId());
                replenishmentVO.setReplenishmentItemNum(stockMix - stockCurrent);
                replenishmentVO.setReplenishmentStatusCode(0);
                replenishmentVO.setReplenishmenShopAddress(userAddress);
//                String decimalPrice = new DecimalFormat("#.##").format(itemHomePageMore.getItemPrice());
//                float decimalFormat = Float.parseFloat(decimalPrice);
                float decimalFormat = Float.parseFloat(itemHomePageMore.getItemPrice());
                replenishmentVO.setReplenishmentItemPrice(String.valueOf((stockMix - stockCurrent) * decimalFormat));

                allPrice = allPrice + (stockMix - stockCurrent) * decimalFormat;

                replenishmentVOS.add(replenishmentVO);
            }
        }
        if (allPrice == 0) {
            return Msg.statu403().add("info", "没有可以补货的订单");
        }

        ReplenishmentPO replenishmentPO = new ReplenishmentPO();
        replenishmentPO.setReplenishmentShopAddress(userAddress);
        replenishmentPO.setReplenishmentAllPrice(String.valueOf(allPrice));
        replenishmentPO.setReplenishmentInfo(JSON.toJSONString(replenishmentVOS));
        replenishmentPO.setReplenishmentStatusCode(0);
        replenishmentRepository.save(replenishmentPO);


        return Msg.statu200().add("info", replenishmentVOS).add("allPrice", allPrice);
    }


    /**
     * 确认补货
     *
     * @param access_token     token
     * @param replenishment_id 补货id
     * @return 1
     */
    @RequestMapping(value = "/updateReplenishment")
    public Msg updateReplenishment(String access_token, String replenishment_id) {
        if (access_token == null || access_token.length() < 1 || replenishment_id == null || replenishment_id.length() < 1) {
            return Msg.statu400();
        }
        if (lspVxUserService.getByToken(access_token) == null) {
            return Msg.statu401();
        }
        ReplenishmentPO replenishmentPO = replenishmentRepository.findByReplenishmentItemId(Integer.parseInt(replenishment_id));
        if (isEmpty(replenishmentPO)) {
            return Msg.statu403().add("info", "找不到数据");
        }
        replenishmentPO.setIsFinish(1);
        replenishmentRepository.save(replenishmentPO);
        return Msg.statu200();
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 查询同寝室订单
     *
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getOrderByShopAddress")
    public Msg getOrderByShopAddress( String access_token) {
        if (lspVxUserService.getByToken(access_token) == null) {
            return Msg.statu401();
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        String user_address = userInfoService.findByOpenid(openid).getUserAddress();
        return Msg.statu200().add("info", shopInfoDao.findByShopAddress(user_address));
    }

    /**
     * 查询补货订单
     *
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getReplenishment")
    public Msg getReplenishment( String access_token) {
        if (lspVxUserService.getByToken(access_token) == null) {
            return Msg.statu401();
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        String user_address = userInfoService.findByOpenid(openid).getUserAddress();
        return Msg.statu200().add("info", replenishmentRepository.findByReplenishmentShopAddress(user_address));
    }

    /**
     * 更新信息(不更新不传参数或参数为空）
     *
     * @param access_token  token
     * @param userTelephone 手机号
     * @param userBuilding  楼栋
     * @param userFloor     楼层
     * @param userDormitory 寝室号
     * @return 1
     */
    @RequestMapping(value = "/updateUserInfo")
    public Msg updateUserInfo( String access_token, String userTelephone,
                                                                      String userBuilding, String userFloor, String userDormitory) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if (!(userTelephone == null || "".equals(userTelephone)))
            userInfoModel.setUserTelephone(userTelephone);
        if (!(userBuilding == null || "".equals(userBuilding)))
            userInfoModel.setUserBuilding(userBuilding);
        if (!(userFloor == null || "".equals(userFloor)))
            userInfoModel.setUserFloor(userFloor);
        if (!(userDormitory == null || "".equals(userDormitory)))
            userInfoModel.setUserDormitory(userDormitory);
        try {
            userInfoDao.save(userInfoModel);
            return Msg.statu200();
        } catch (Exception e) {
            return Msg.statu403().add("error", e.getMessage());
        }

    }

    /**
     * 获取所有商品
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getAllItem")
    public Msg getAllItem( String access_token){
        if (lspVxUserService.getByToken(access_token) == null) {
            return Msg.statu401();
        }
        return Msg.statu200().add("info",itemAllDao.findAll());
    }



}
