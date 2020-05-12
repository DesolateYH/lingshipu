package com.example.demo.Html.controller;

import com.example.demo.Html.SessionUtil;
import com.example.demo.Html.repository.*;
import com.example.demo.Html.domian.po.AdminInfoModel;
import com.example.demo.Html.domian.po.ShopInfo;
import com.example.demo.Html.service.ItemAllServiceImpl;
import com.example.demo.Html.service.ItemServiceImpl;
import com.example.demo.Html.service.ShopInfoServiceImpl;
import com.example.demo.Html.service.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: lingshipu
 * @description: 管理员页面控制类
 * @author: QWS
 * @create: 2020-03-01 19:55
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/admin")
public class LspAdminController {
    @Autowired
    ShopInfoDao shopInfoDao;
    @Autowired
    ItemAllServiceImpl itemAllService;
    @Autowired
    ItemAllDao itemAllDao;
    @Autowired
    ItemHomePageMoreDao itemHomePageMoreDao;
    @Autowired
    AdminInfoDao adminInfoDao;

    final
    ShopInfoServiceImpl shopInfoService;
    final
    ItemServiceImpl itemService;
    final
    UserInfoServiceImpl userInfoService;

    public LspAdminController(ShopInfoServiceImpl shopInfoService, ItemServiceImpl itemService, UserInfoServiceImpl userInfoService) {
        this.shopInfoService = shopInfoService;
        this.itemService = itemService;
        this.userInfoService = userInfoService;
    }


    /**
     * 获取店铺列表
     *
     * @return
     */
    @RequestMapping(value = "/findAllShopInfo")
    public Map<String, Object> findAallShopInfo(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Map<String, Object> map = new HashMap<>();
        try {
            String user_id = session.getAttribute("user_id").toString();
            if ("-2".equals(userInfoService.findByUserId(user_id).getPermissions())) {
                map.put("msg", "权限不足");
            } else {
                map.put("ShopInfo", userInfoService.findShopInfoByAdminPermissions(user_id));
            }
            return map;
        } catch (Exception e) {
            map.put("status", "500");
            map.put("msg", "请先登录");
            return map;
        }
    }

    /**
     * 获取店铺内商品列表
     *
     * @param shop_id 店铺id
     * @return 商品列表
     */
    @RequestMapping(value = "/findItemInfoByShopId")
    public Map<String, Object> findItemInfoByShopId(HttpServletRequest httpServletRequest, Integer shop_id) {
        HttpSession session = httpServletRequest.getSession();
        Map<String, Object> map = new HashMap<>();
        if (shop_id == null) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }
        try {
            String user_id = session.getAttribute("user_id").toString();
            if ("-2".equals(userInfoService.findByUserId(user_id).getPermissions())) {
                map.put("msg", "权限不足");
            } else {
                map.put("itemInfo", itemService.findByItemInfoByShopId(shop_id));
                String shop_address = shopInfoService.findShopInfoByShopId(shop_id).getShopAddress();
                map.put("userInfo", userInfoService.findUserInfoByUserAddress(shop_address));
            }
            return map;
        } catch (Exception e) {
            map.put("status", "500");
            map.put("msg", "未知错误");
            return map;
        }

    }

    /**
     * 添加寝室
     *
     * @param shop_address 寝室号
     * @return 200/500
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/addShop")
    public Map<String, Object> addShop(String shop_address) {
        Map<String, Object> map = new HashMap<>();
        if (shopInfoService.findExistShopByShopAddress(shop_address)) {
            map.put("status", "500");
            map.put("msg", "寝室已存在");
            return map;
        }
        if (shop_address == null || shop_address.length() < 1) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }


        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopAddress(shop_address);
        shopInfo.setTotalSales("0");
        shopInfo.setUserNum(0);
        shopInfoDao.save(shopInfo);
        map.put("shop_id", shopInfoDao.findByShopAddress(shop_address).getShopId());
        return map;
    }

    /**
     * 删除寝室及附属商品
     *
     * @param shop_id 寝室id
     * @return 200/500
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/deleteShopByShopId")
    public Map<String, Object> deleteShopByShopId(Integer shop_id) {
        Map<String, Object> map = new HashMap<>();
        String user_id = SessionUtil.getSessionAttribute("user_id").toString();
        if (!"-1".equals(userInfoService.findByUserId(user_id).getPermissions())) {
            map.put("msg", "权限不足");
            return map;
        }
        if (shop_id == null) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }
        if (shopInfoService.deleteShopInfoByShopId(shop_id) && itemService.deleteItemByShopId(shop_id) && userInfoService.deletePermissionsForAdmin(String.valueOf(shop_id))) {
            map.put("status", "200");
            map.put("msg", "寝室删除成功");
            return map;
        } else {
            map.put("status", "500");
            map.put("msg", "寝室删除失败");
            return map;
        }
    }


    /**
     * 获取所有商品列表
     *
     * @return 商品列表
     */
    @RequestMapping(value = "/findAllItem")
    public Map<String, Object> findAllItem() {
        Map<String, Object> map = new HashMap<>();
        String user_id = SessionUtil.getSessionAttribute("user_id").toString();
        if (!"-2".equals(userInfoService.findByUserId(user_id).getPermissions())
                && !"-1".equals(userInfoService.findByUserId(user_id).getPermissions())) {
            map.put("msg", "权限不足");
            return map;
        }
        map.put("AllItemInfo", itemAllService.findAllItemAll());
        return map;
    }

    /**
     * 往寝室内添加商品
     *
     * @param shop_id   寝室id
     * @param item_id   商品id
     * @param stock_mix 最大库存
     * @return 200/500
     */
    @RequestMapping(value = "/addItemToShop")
    public Map<String, Object> addItemToShop(Integer shop_id, Integer item_id, Integer stock_mix) {
        Map<String, Object> map = new HashMap<>();
        if (shop_id == null || item_id == null || stock_mix == null) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }
        if (shopInfoDao.findByShopId(shop_id) == null) {
            map.put("status", "500");
            map.put("msg", "寝室不存在");
            return map;
        }
        if (itemAllDao.findByItemId(item_id) == null) {
            map.put("status", "500");
            map.put("msg", "商品在商品总表中不存在");
            return map;
        }
        if (stock_mix < 0) {
            map.put("status", "500");
            map.put("msg", "库存输入有误");
            return map;
        }
        if (itemHomePageMoreDao.findByItemIdAndParentShopId(item_id, shop_id) != null) {
            map.put("status", "500");
            map.put("msg", "商品在所选寝室中已存在");
            return map;
        }

        try {
           /* itemService.addItemToShop(shop_id, item_id, stock_mix);
            itemAllService.updateInventoryBalance(item_id, -stock_mix);
            itemAllService.updateStockCurrentAll(item_id, stock_mix);*/
            itemAllService.addItemToShopTransactional(shop_id, item_id, stock_mix);
            map.put("status", "200");
            map.put("msg", "添加成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "500");
            map.put("msg", "添加失败");
            return map;
        }

    }

    /**
     * 删除寝室内的商品
     *
     * @param httpServletRequest 1
     * @param shop_id            寝室id
     * @param item_id            商品id
     * @return
     */
    @RequestMapping(value = "/deleteItemFromShop")
    public Map<String, Object> deleteItemFromShop(HttpServletRequest httpServletRequest, Integer shop_id, Integer item_id) {
        HttpSession session = httpServletRequest.getSession();
        Map<String, Object> map = new HashMap<>();
        if (shop_id == null || item_id == null) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }
        try {
            String user_id = session.getAttribute("user_id").toString();
            if ("-1".equals(user_id)) {
                if (itemService.deleteItemFromShop(shop_id, item_id)) {
                    map.put("status", "200");
                    map.put("msg", "删除成功");
                } else {
                    map.put("status", "500");
                    map.put("msg", "删除失败");
                }
                return map;
            } else {
                map.put("status", "500");
                map.put("msg", "删除失败");
                return map;
            }
        } catch (Exception e) {
            map.put("status", "500");
            map.put("msg", "请先登录");
            return map;
        }
    }

    /**
     * 管理员登陆
     *
     * @param user_id  用户名
     * @param password 密码
     * @return 200/500
     */
    @RequestMapping(value = "/adminLogin")
    public Map<String, Object> adminLogin(HttpServletRequest request, String user_id, String password) {
        AdminInfoModel adminInfoModel = userInfoService.findByUserIdAndPassword(user_id, password);
        Map<String, Object> map = new HashMap<>();
        if (user_id == null || password == null || user_id.length() < 1 || password.length() < 1) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }
        if (adminInfoModel == null) {
            map.put("status", "500");
            map.put("msg", "用户名密码错误");
            return map;
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user_id", adminInfoModel.getUserId());
            if ("-1".equals(userInfoService.getAdminPermissions(user_id))) {
                map.put("Info", "-1");
            } else if ("-2".equals(userInfoService.getAdminPermissions(user_id))) {
                map.put("Info", "-2");
            }else {
                map.put("Info", "0");
            }
            return map;
        }
    }

    /**
     * 添加管理员账号
     *
     * @param httpServletRequest
     * @param user_id            用户名
     * @param password           密码
     * @param nickname           昵称
     * @param permissions        权限字符串（shop id）
     * @return 200/500
     */
    @RequestMapping(value = "/addAdminInfo")
    public Map<String, Object> addAdminInfo(HttpServletRequest httpServletRequest, String user_id, String password,
                                            String nickname, String permissions) {
        HttpSession session = httpServletRequest.getSession();
        Map<String, Object> map = new HashMap<>();
        if (user_id == null || password == null || nickname == null || permissions == null || user_id.length() < 1 ||
                password.length() < 1 || nickname.length() < 1 || permissions.length() < 1) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }
        try {
            String user_idSession = session.getAttribute("user_id").toString();
            if ("-1".equals(userInfoService.findByUserId(user_idSession).getPermissions())) {

                String[] permissionsAddressArrary = permissions.split("\\|");
                StringBuilder stringBuilder = new StringBuilder();
                for (String v : permissionsAddressArrary) {
                    String shop_id = String.valueOf(shopInfoDao.findByShopAddress(v).getShopId());
                    stringBuilder.append(shop_id).append("|");
                }
                AdminInfoModel adminInfoModel = new AdminInfoModel();
                adminInfoModel.setPassword(password);
                adminInfoModel.setUserId(user_id);
                adminInfoModel.setNickName(nickname);
                adminInfoModel.setPermissions(stringBuilder.toString());
                adminInfoDao.save(adminInfoModel);
                map.put("status", "200");
                map.put("msg", "添加成功");
                ;
            } else {
                map.put("status", "500");
                map.put("msg", "权限不足");
            }
            return map;
        } catch (Exception e) {
            map.put("status", "500");
            map.put("msg", "请先登录");
            return map;
        }
    }

    /**
     * 拦截器重定向
     */
    @RequestMapping(value = "/adminLoginError")
    public Map<String, Object> adminLoginError() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "500");
        map.put("msg", "请先登录");
        return map;
    }

    /**
     * 修改管理员权限
     *
     * @param httpServletRequest
     * @param user_id            管理员id
     * @param permissions        权限列表篇
     * @return
     */
    @RequestMapping(value = "/adminAddPermissions")
    public Map<String, Object> adminAddpermissions(HttpServletRequest httpServletRequest, String user_id, String permissions) {
        HttpSession session = httpServletRequest.getSession();
        Map<String, Object> map = new HashMap<>();
        if (user_id == null || permissions == null || user_id.length() < 1 || permissions.length() < 1) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }
        try {
            String user_idSession = session.getAttribute("user_id").toString();
            if ("-1".equals(userInfoService.findByUserId(user_idSession).getPermissions())) {
                String[] permissionsAddressArrary = permissions.split("\\|");
                StringBuilder stringBuilder = new StringBuilder();
                for (String v : permissionsAddressArrary) {
                    String shop_id = String.valueOf(shopInfoDao.findByShopAddress(v).getShopId());
                    stringBuilder.append(shop_id).append("|");
                }
                AdminInfoModel adminInfoModel = adminInfoDao.findByUserId(user_id);
                adminInfoModel.setPermissions(stringBuilder.toString());
                adminInfoDao.save(adminInfoModel);
                map.put("status", "200");
                map.put("msg", "添加成功");
            } else {
                map.put("status", "500");
                map.put("msg", "权限不足");
            }
            return map;
        } catch (Exception e) {
            map.put("status", "500");
            map.put("msg", "修改失败");
            System.out.println("Error：添加管理员权限");
            return map;
        }
    }

    /**
     * 超极管理员获取所有管理员信息
     *
     * @return
     */
    @RequestMapping(value = "/getAllAdminInfo")
    public Map<String, Object> getAllAdminInfo(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Map<String, Object> map = new HashMap<>();
        String user_id = session.getAttribute("user_id").toString();
        if ("-1".equals(userInfoService.getAdminPermissions(user_id))) {
            List<AdminInfoModel> adminInfoModelsPO = adminInfoDao.findAll();
            for (AdminInfoModel adminInfoModel : adminInfoModelsPO) {
                if ("-1".equals(adminInfoModel.getPermissions())) {
                    adminInfoModel.setPermissions("全部|");
                    continue;
                }
                if ("-2".equals(adminInfoModel.getPermissions())) {
                    adminInfoModel.setPermissions("仓管|");
                    continue;
                }
                List<ShopInfo> shopInfos = userInfoService.findShopInfoByAdminPermissions(adminInfoModel.getUserId());
                StringBuilder shopAddressVO = new StringBuilder("");
                for (ShopInfo shopInfo : shopInfos) {
                    String shopAddress = shopInfo.getShopAddress();
                    shopAddressVO.append(shopAddress).append("|");
                }
                adminInfoModel.setPermissions(shopAddressVO.toString());
            }
            map.put("AdminInfo", adminInfoModelsPO);
            return map;
        } else {
            map.put("status", "500");
            map.put("msg", "权限不足");
            return map;
        }
    }

}


