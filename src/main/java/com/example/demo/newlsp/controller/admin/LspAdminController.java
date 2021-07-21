package com.example.demo.newlsp.controller.admin;

import com.alibaba.fastjson.JSON;
import com.example.demo.html.controller.AuthUtil;
import com.example.demo.html.controller.HttpRequest;
import com.example.demo.html.controller.WXPayUtil;
import com.example.demo.html.domian.po.*;
import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.repository.*;
import com.example.demo.html.service.*;
import com.example.demo.newlsp.domain.po.InventoryRecordPO;
import com.example.demo.newlsp.repository.InventoryRecordRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static com.example.demo.html.ToolUntil.uploadFile;

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
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    ReplenishmentServiceImpl replenishmentService;
    @Autowired
    ReplenishmentRepository replenishmentRepository;
    @Autowired
    LspOrderInfoRepository lspOrderInfoRepository;
    @Autowired
    LspOrderInfoServiceImpl lspOrderInfoService;
    @Autowired
    SortPageRepository sortPageRepository;
    @Autowired
    InventoryRecordRepository inventoryRecordRepository;
    @Autowired
    OperationRecordController operationRecordController;


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
     * @param access_token       token
     * @param httpServletRequest httpServletRequest
     * @return 1
     */
    @RequestMapping(value = "/findAllShopInfo")
    public Msg findAallShopInfo(String access_token, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>();
        if (access_token != null) {
            String openid = lspVxUserService.getOpenidByToken(access_token);
            UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
            String adminId = userInfoModel.getAdminId();
            AdminInfoModel adminInfoModel = userInfoService.findByUserId(adminId);
            if (adminInfoModel == null) {
                return Msg.statu401();
            }
            return Msg.statu200().add("info", userInfoService.findShopInfoByAdminPermissions(adminInfoModel.getUserId()));

        }
        HttpSession session = httpServletRequest.getSession();

        try {
            String user_id = session.getAttribute("user_id").toString();
            if ("-2".equals(userInfoService.findByUserId(user_id).getPermissions())) {
                return Msg.statu401();
            } else {
                return Msg.statu200().add("info", userInfoService.findShopInfoByAdminPermissions(user_id));
            }

        } catch (Exception e) {
            return Msg.statu401();
        }
    }

    /**
     * 获取店铺内商品列表
     *
     * @param access_token token
     * @param shop_id      店铺id
     * @return 商品列表
     */
    @RequestMapping(value = "/findItemInfoByShopId")
    public Map<String, Object> findItemInfoByShopId(String access_token, HttpServletRequest httpServletRequest, Integer shop_id) {
        Map<String, Object> map = new HashMap<>();

        if (access_token != null) {
            String openid = lspVxUserService.getOpenidByToken(access_token);
            UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
            String adminId = userInfoModel.getAdminId();
            AdminInfoModel adminInfoModel = userInfoService.findByUserId(adminId);
            if (adminInfoModel == null) {
                map.put("msg", "权限不足");
                return map;
            }
            map.put("itemfindInfo", itemService.findByItemInfoByShopId(shop_id));
            ShopInfo shopInfo = shopInfoService.findShopInfoByShopId(shop_id);
            String shop_address = shopInfo.getShopAddress();
            map.put("userInfo", userInfoService.findUserInfoByUserAddress(shop_address));
            map.put("lastCheckTime", shopInfo.getShopLastCheckTime());
            return map;
        }

        HttpSession session = httpServletRequest.getSession();

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
     * @param access_token token
     * @param shop_address 寝室号
     * @return 200/500
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/addShop")
    public Msg addShop( String access_token,  String shop_address) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        Map<String, Object> map = new HashMap<>();
        if (shopInfoService.findExistShopByShopAddress(shop_address)) {
            return Msg.statu403().add("error", "寝室已存在");
        }

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopAddress(shop_address);
        shopInfo.setTotalSales("0");
        shopInfo.setUserNum(0);
        shopInfo.setShopNotic("");
        shopInfo.setShopCreditScore(80);
        shopInfoDao.save(shopInfo);

        List<ItemAllModel> itemAllModels = new ArrayList<>();
        operationRecordController.saveOpeartionRecord(userInfoModel.getAdminId(), itemAllModels, "添加寝室:" + shop_address);

        return Msg.statu200().add("info", shopInfoDao.findByShopAddress(shop_address).getShopId());
    }

    /**
     * 删除寝室及附属商品
     *
     * @param access_token token
     * @param shop_id      寝室id
     * @return 200/500
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/deleteShopByShopId")
    public Msg deleteShopByShopId( String access_token,  Integer shop_id) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        if (shopInfoService.deleteShopInfoByShopId(shop_id) && itemService.deleteItemByShopId(shop_id) && userInfoService.deletePermissionsForAdmin(String.valueOf(shop_id))) {
            List<ItemAllModel> itemAllModels = new ArrayList<>();
            operationRecordController.saveOpeartionRecord(userInfoModel.getAdminId(), itemAllModels, "删除寝室:" + shop_id);

            return Msg.statu200();
        } else {
            return Msg.statu403().add("error", "删除失败");
        }
    }


    /**
     * 获取所有商品列表
     *
     * @param access_token token
     * @return 商品列表
     */
    @RequestMapping(value = "/findAllItem")
    public Msg findAllItem( String access_token) throws InterruptedException {
        if (access_token == null || access_token.length() < 1) {
            return Msg.statu400();
        }
        long listSize = itemAllDao.count();
        int runSize = 3;
        List<ItemAllModel> itemAllModels = new CopyOnWriteArrayList<>();//存放返回结果
        CountDownLatch countDownLatch = new CountDownLatch(runSize);
        ExecutorService executorService = Executors.newFixedThreadPool(runSize);
        int count = Math.toIntExact(listSize / runSize);
        for (int i = 0; i < runSize+1; i++) {
            int index = i * count;
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println(index + "|" + count);
                    List<ItemAllModel> itemAllModels1 = itemAllDao.findAllPage(index, count);
                    itemAllModels.addAll(itemAllModels1);
                    System.out.println("当前线程name : " + Thread.currentThread().getName());
                    countDownLatch.countDown();
                }

            };
            executorService.execute(runnable);
        }
        countDownLatch.await();
        executorService.shutdown();
        return Msg.statu200().add("info", itemAllModels);


//        int listSize = 100;
//        // 开启的线程数
//        int runSize = 20;
//        // 一个线程处理数据条数，如果库中有100条数据，开启20个线程，那么每一个线程执行的条数就是5条
//        int count = listSize / runSize;//5
//        // 创建一个线程池，数量和开启线程的数量一样
//        ExecutorService executor = Executors.newFixedThreadPool(runSize);
//
//        // 循环创建线程
//        List<ItemAllModel> itemAllModels = new ArrayList<>();
//        List<String> strings = new ArrayList<>();
//        for (int i = 0; i < runSize; i++) {
//            int index = i * count;
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        System.out.println(index+":"+count);
//                        //查询的结果如何保存下来，会不会存在覆盖的问题
//                        List<ItemAllModel> list = itemAllDao.findAllPage(index , count);
//                        strings.add("1");
//                        //这里做成写入文件的方法
//                    } catch (Exception e) {
//                        System.out.println("查询失败" + e);
//                    }
//                }
//            });
//        }
//        // 执行完关闭线程池
//        executor.shutdown();
//        for(String s : strings){
//            System.out.println(s);
//        }
//        return Msg.statu200().add("info", strings);
    }

    /**
     * 往寝室内添加商品
     *
     * @param access_token token
     * @param itemJson     商品json
     * @param shop_id      寝室id
     * @return 异常
     */
    @RequestMapping(value = "/addItemToShop")
    @Transactional(rollbackFor = Exception.class)
    public Msg addItemToShop( String access_token, String itemJson, Integer shop_id) throws Exception {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        if (shopInfoDao.findByShopId(shop_id) == null) {
            return Msg.statu403().add("error", "寝室不存在");
        }
        JSONArray jsonArray = JSONArray.fromObject(itemJson);
        for (int i = 0; i < jsonArray.size(); i++) {
            int itemId = jsonArray.getJSONObject(i).getInt("itemId");
            int stockMax = jsonArray.getJSONObject(i).getInt("num");
            if (itemAllDao.findByItemId(itemId) == null) {
                throw new Exception("商品id[" + itemId + "]不存在");
            }
            if (stockMax < 0) {
                throw new Exception("库存输入有误");
            }
            if (itemHomePageMoreDao.findByItemIdAndParentShopId(itemId, shop_id) != null) {
                throw new Exception("商品在所选寝室中已存在");
            }
            int sortId = itemAllDao.findByItemId(itemId).getItemSortId();

            try {
           /* itemService.addItemToShop(shop_id, item_id, stock_mix);
            itemAllService.updateInventoryBalance(item_id, -stock_mix);
            itemAllService.updateStockCurrentAll(item_id, stock_mix);*/
                itemAllService.addItemToShopTransactional(shop_id, itemId, stockMax, sortId);
                ItemAllModel itemAllModel = itemAllDao.findByItemId(itemId);
                List<ItemAllModel> itemAllModels = new ArrayList<>();
                itemAllModels.add(itemAllModel);
                operationRecordController.saveOpeartionRecord(userInfoModel.getAdminId(), itemAllModels, "往寝室:" + shop_id + "添加商品");
            } catch (Exception e) {
                return Msg.statu403().add("errer", e.getMessage());
            }
        }
        return Msg.statu200();
    }

    /**
     * 删除寝室内的商品
     *
     * @param access_token token
     * @param shop_id      寝室id
     * @param item_id      商品id
     * @return 1
     */
    @RequestMapping(value = "/deleteItemFromShop")
    public Msg deleteItemFromShop( String access_token,  Integer shop_id,
                                   Integer item_id) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }

        String user_id = String.valueOf(userInfoDao.findByOpenid(openid).getUserId());

        try {
            if ("-1".equals(user_id)) {
                if (itemService.deleteItemFromShop(shop_id, item_id)) {
                    return Msg.statu200();
                } else {
                    return Msg.statu403().add("error", "删除失败");
                }

            } else {
                return Msg.statu403().add("error", "删除失败");
            }
        } catch (Exception e) {
            return Msg.statu403().add("error", "删除失败");
        }
    }

    /**
     * 管理员登陆(已经没用）
     *
     * @param user_id  用户名
     * @param password 密码
     * @return 200/500
     */
    @RequestMapping(value = "/adminLogin")
    public Map<String, Object> adminLogin(HttpServletRequest request, String user_id, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("Info", "-1");
        return map;
      /*  AdminInfoModel adminInfoModel = userInfoService.findByUserIdAndPassword(user_id, password);
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
            } else {
                map.put("Info", "0");
            }
            return map;
        }*/
    }

    /**
     * 添加管理员账号（已经没用）
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
     * @param access_token token
     * @param user_id      管理员id
     * @param permissions  权限列表篇
     * @return
     */
    @RequestMapping(value = "/adminAddPermissions")
    public Map<String, Object> adminAddpermissions(  String access_token, String user_id, String permissions) {

        Map<String, Object> map = new HashMap<>();
        if (user_id == null || permissions == null || user_id.length() < 1 || permissions.length() < 1) {
            map.put("status", "500");
            map.put("msg", "参数有误");
            return map;
        }
        try {


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
     * @param access_token token
     * @return
     */
    @RequestMapping(value = "/getAllAdminInfo")
    public Map<String, Object> getAllAdminInfo( String access_token) {

        Map<String, Object> map = new HashMap<>();


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

    }

    /**
     * 绑定权限
     *
     * @param access_token token
     * @param userLogin    用户名
     * @param userPassword 密码
     * @return 1
     */
    @RequestMapping(value = "/bindPermission")
    public Msg bindPermission(String access_token, String userLogin, String userPassword) {
        if (access_token == null || userLogin == null || userPassword == null ||
                access_token.length() < 1 || userLogin.length() < 1 || userPassword.length() < 1) {
            return Msg.statu400();
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        AdminInfoModel adminInfoModel = userInfoService.findByUserIdAndPassword(userLogin, userPassword);
        if (adminInfoModel == null) {
            return Msg.statu403().add("info", "密码错误");
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        userInfoModel.setAdminId(adminInfoModel.getUserId());
        userInfoDao.save(userInfoModel);
        return Msg.statu200();
    }

    /**
     * 查询补货订单
     *
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/findReplenishmentInfo")
    public Msg findReplenishmentInfo(String access_token) {
        if (access_token == null || access_token.length() < 1) {
            return Msg.statu400();
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        AdminInfoModel adminInfoModel = userInfoService.findByUserId(userInfoModel.getAdminId());
        if ("-1".equals(adminInfoModel.getPermissions()) || "-2".equals(adminInfoModel.getPermissions())) {
            return Msg.statu200().add("info", replenishmentRepository.findAll());
        } else {
            String[] shopId = adminInfoModel.getPermissions().split("\\|");
            List<ReplenishmentPO> replenishmentPOS = new ArrayList<>();
            for (String s : shopId) {
                ShopInfo shopInfo = shopInfoService.findShopInfoByShopId(Integer.parseInt(s));
                List<ReplenishmentPO> replenishmentPOS1 = replenishmentRepository.findByReplenishmentShopAddress(shopInfo.getShopAddress());
                replenishmentPOS.addAll(replenishmentPOS1);
            }
            return Msg.statu200().add("info", replenishmentPOS);
        }
    }

    /**
     * 管理员盘货（盘盈/盘亏）
     *
     * @param access_token token
     * @param shop_id      寝室id
     * @param shop_json    商品json
     * @param state        盘盈/盘亏 描述（1或者2）
     * @return 1
     */
    @RequestMapping(value = "/checkShop")
    public Map<String, String> checkShop(String access_token,
                                         String shop_id, String shop_json, HttpServletRequest request,
                                         Integer state) {
        Map<String, String> map = new HashMap<>();
        if (shop_id == null || shop_id.length() < 1 || shop_json == null || shop_json.length() < 1) {
            map.put("msg", "400");
            return map;
        }
        if (access_token == null || access_token.length() < 1) {
            map.put("msg", "400");
            return map;
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            map.put("msg", "401");
            return map;
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            map.put("msg", "401");
            return map;
        }
        if (state != 1 && state != 2) {
            map.put("msg", "403");
            return map;
        }


//        AdminInfoModel adminInfoModel = userInfoService.findByUserId(userInfoModel.getAdminId());
        //不完美
//        if ("-1".equals(adminInfoModel.getPermissions()) || "-2".equals(adminInfoModel.getPermissions()) || adminInfoModel.getPermissions().contains(shop_id)) {
        JSONArray itemInfoObjectArrary = JSONArray.fromObject(JSON.parseArray(shop_json));
        float allMoney = 0;
        for (Object obj : itemInfoObjectArrary) {
            JSONObject jsonObject1 = (JSONObject) obj;
            float itemPrice = Float.parseFloat(jsonObject1.getString("itemPrice"));
            int itemNum = jsonObject1.getInt("defectNum");
            float singleItemMoney = itemNum * itemPrice;
            allMoney += singleItemMoney;
        }
        ShopInfo shopInfo = shopInfoService.findShopInfoByShopId(Integer.parseInt(shop_id));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        shopInfo.setShopLastCheckTime(df.format(new Date()));
        shopInfoDao.save(shopInfo);


        float cart_price_total = allMoney;
        String userAddress = userInfoService.findByOpenid(openid).getUserAddress();
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
            paraMap.put("body", "寝室零食盒子缺货补交 " + "\n" + "盒子地址:" + shopAddress + "\n" + "付款人地址:" + userAddress); // 商家名称-销售商品类目、String(128)
            paraMap.put("mch_id", AuthUtil.MCHID); // 商户ID
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr()); // UUID
            paraMap.put("openid", openid);

            String out_trade_no = UUID.randomUUID().toString().replaceAll("-", "");
            paraMap.put("out_trade_no", out_trade_no);// 订单号,每次都不同

            paraMap.put("spbill_create_ip", ip);

            String total_fee = String.valueOf(cart_price_total * 100);
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
            lspOrderInfoPO.setOrderItemJson(shop_json);
            lspOrderInfoPO.setOrderTime(df.format(new Date()));
            lspOrderInfoPO.setOrderState(0);
            lspOrderInfoPO.setVxUserOpenid(openid);
            lspOrderInfoPO.setOrderOutTradeNo(out_trade_no);
            lspOrderInfoPO.setShopAddress(shopAddress);
            lspOrderInfoPO.setUserAddress(userAddress);
            lspOrderInfoService.saveOrderInfo(lspOrderInfoPO);

            InventoryRecordPO inventoryRecordPO = new InventoryRecordPO();
            inventoryRecordPO.setInventoryRecordShopId(Integer.parseInt(shop_id));
            inventoryRecordPO.setInventoryRecordShopName(shopInfoService.findShopInfoByShopId(Integer.parseInt(shop_id)).getShopAddress());
            inventoryRecordPO.setInventoryRecordIsDefect(state);
            inventoryRecordPO.setInventoryRecordDefectJson(shop_json);
            inventoryRecordPO.setInventoryRecordUserName(userInfoModel.getUserName());
            inventoryRecordPO.setInventoryRecordOpenId(userInfoModel.getOpenid());
            inventoryRecordPO.setInventoryRecordUserId(userInfoModel.getUserId());
            inventoryRecordRepository.save(inventoryRecordPO);

            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("appId", AuthUtil.APPID);
            payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(payMap, AuthUtil.PATERNERKEY);
            payMap.put("paySign", paySign);
            //将这个6个参数传给前端
            return payMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


        /*} else {
            map.put("msg", "401");
            return map;
        }*/
    }

    /**
     * 管理员盘货（盘正）
     *
     * @param access_token token
     * @param shop_id      寝室id
     * @return 1
     */
    @RequestMapping(value = "/checkShopPass")
    public Msg checkShopPass(String access_token, String shop_id) {
        if (shop_id == null || shop_id.length() < 1) {
            return Msg.statu400();
        }
        if (access_token == null || access_token.length() < 1) {
            return Msg.statu400();
        }
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        AdminInfoModel adminInfoModel = userInfoService.findByUserId(userInfoModel.getAdminId());
        //不完美
        if ("-1".equals(adminInfoModel.getPermissions()) || "-2".equals(adminInfoModel.getPermissions()) || adminInfoModel.getPermissions().contains(shop_id)) {
            ShopInfo shopInfo = shopInfoService.findShopInfoByShopId(Integer.parseInt(shop_id));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            shopInfo.setShopLastCheckTime(df.format(new Date()));
            shopInfoDao.save(shopInfo);

            InventoryRecordPO inventoryRecordPO = new InventoryRecordPO();
            inventoryRecordPO.setInventoryRecordShopId(Integer.parseInt(shop_id));
            inventoryRecordPO.setInventoryRecordShopName(shopInfoService.findShopInfoByShopId(Integer.parseInt(shop_id)).getShopAddress());
            inventoryRecordPO.setInventoryRecordIsDefect(0);
            inventoryRecordPO.setInventoryRecordDefectJson("无");
            inventoryRecordPO.setInventoryRecordUserName(userInfoModel.getUserName());
            inventoryRecordPO.setInventoryRecordOpenId(userInfoModel.getOpenid());
            inventoryRecordPO.setInventoryRecordUserId(userInfoModel.getUserId());
            inventoryRecordRepository.save(inventoryRecordPO);

            return Msg.statu200();
        } else {
            return Msg.statu401();
        }
    }


    /**
     * 添加分类
     *
     * @param access_token token
     * @param sortName     分类名
     * @param pic_address  图片地址
     * @param file         分类图片
     * @return 1
     */
    @RequestMapping(value = "/addSort")
    public Msg addSort( String access_token,  String sortName,  String pic_address,
                       MultipartFile file) throws Exception {


        String openid = lspVxUserService.getOpenidByToken(access_token);
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        String url = uploadFile(file);
        if ("error".equals(url)) {
            return Msg.statu403().add("error", "图片上传失败");
        }
        SortPagePO sortPagePO = new SortPagePO();
        sortPagePO.setSortPhotoAddress(url);
        sortPagePO.setSortName(sortName);
        sortPagePO.setSortPhotoAddress(pic_address);
        sortPageRepository.save(sortPagePO);
        return Msg.statu200();
    }

    /**
     * 获取分类
     *
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getSort")
    public Msg getSort( String access_token) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        return Msg.statu200().add("info", sortPageRepository.findAll());
    }

    /**
     * 删除分类
     *
     * @param access_token token
     * @param sortId       分类id
     * @return 1
     */
    @Transactional
    @RequestMapping(value = "/deleteSort")
    public Msg deleteSort( String access_token,  Integer sortId) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        try {
            sortPageRepository.deleteById(sortId);
            return Msg.statu200();
        } catch (Exception e) {
            return Msg.statu403().add("error", e.getMessage());
        }
    }

    /**
     * 更新分类
     *
     * @param access_token token
     * @param sortId       分类id
     * @param sortName     分类名
     * @param file         图片
     * @return 1
     */
    @Transactional
    @RequestMapping(value = "/updateSort")
    public Msg updateSort( String access_token,  Integer sortId,  String sortName,
                          MultipartFile file) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        if (sortPageRepository.findBySortId(sortId) == null) {
            return Msg.statu403().add("error", "没有查到分类信息");
        }
        try {
            String url = uploadFile(file);
            if ("error".equals(url)) {
                return Msg.statu403().add("error", "图片上传失败");
            }
            SortPagePO sortPagePO = new SortPagePO();
            sortPagePO.setSortPhotoAddress(url);
            sortPagePO.setSortId(sortId);
            sortPagePO.setSortName(sortName);
            sortPageRepository.save(sortPagePO);
            return Msg.statu200();
        } catch (Exception e) {
            return Msg.statu403().add("error", e.getMessage());
        }
    }


    /**
     * 获取订单，模糊查询
     *
     * @param access_token token（不能为空）
     * @param pageNum      页数（不能为空）
     * @param size         一页的条数（不能为空）
     * @param sortField    排序字段，1订单id，2下单时间，3寝室，4openid（为空默认为订单id）
     * @param sortType     排序类型，1升序，2降序（为空默认为降序）
     * @param likeFlag     是否根据寝室号模糊查询，1为模糊查询，0为不模糊查询（不能为空）
     * @param likeInfo     模糊查询寝室号数据，likeFlag为0则填写词参数无效
     * @return 1
     */
    @RequestMapping(value = "/getAllOrder")
    public Msg getAllOrder( String access_token,  Integer pageNum,  Integer size,
                           Integer sortField, Integer sortType,  Integer likeFlag, String likeInfo) {
        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);
        if ("0".equals(userInfoModel.getAdminId())) {
            return Msg.statu401();
        }
        if (pageNum < 0) {
            return Msg.statu400().add("info", "页数错误");
        }
        String sortFieldd = "orderId";
        if (sortField != null) {
            if (sortField == 1) {
                sortFieldd = "orderId";
            } else if (sortField == 2) {
                sortFieldd = "orderTime";
            } else if (sortField == 3) {
                sortFieldd = "shopAddress";
            } else if (sortField == 4) {
                sortFieldd = "vxUserOpenid";
            }
        }
        if (sortType != null) {
            if (sortType == 1) {
                if (likeFlag == 0) {
                    Sort sort = new Sort(Sort.Direction.ASC, sortFieldd);
                    Pageable pageable = PageRequest.of(--pageNum, size, sort);
                    return Msg.statu200().add("info", lspOrderInfoRepository.findAll(pageable));
                } else {
                    Sort sort = new Sort(Sort.Direction.ASC, sortFieldd);
                    Pageable pageable = PageRequest.of(--pageNum, size, sort);
                    return Msg.statu200().add("info", lspOrderInfoRepository.findByShopAddressLike("%" + likeInfo + "%", pageable));
                }
            } else if (sortType == 2) {
                if (likeFlag == 0) {
                    Sort sort = new Sort(Sort.Direction.DESC, sortFieldd);
                    Pageable pageable = PageRequest.of(--pageNum, size, sort);
                    return Msg.statu200().add("info", lspOrderInfoRepository.findAll(pageable));
                } else {
                    Sort sort = new Sort(Sort.Direction.ASC, sortFieldd);
                    Pageable pageable = PageRequest.of(--pageNum, size, sort);
                    return Msg.statu200().add("info", lspOrderInfoRepository.findByShopAddressLike("%" + likeInfo + "%", pageable));

                }
            }
        }
        if (likeFlag == 0) {
            Sort sort = new Sort(Sort.Direction.DESC, sortFieldd);
            Pageable pageable = PageRequest.of(--pageNum, size, sort);
            return Msg.statu200().add("info", lspOrderInfoRepository.findAll(pageable));
        } else {
            Sort sort = new Sort(Sort.Direction.ASC, sortFieldd);
            Pageable pageable = PageRequest.of(--pageNum, size, sort);
            return Msg.statu200().add("info", lspOrderInfoRepository.findByShopAddressLike("%" + likeInfo + "%", pageable));


        }
    }


}


