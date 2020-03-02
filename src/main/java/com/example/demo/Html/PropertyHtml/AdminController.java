package com.example.demo.Html.PropertyHtml;

import com.example.demo.Html.dao.ShopInfoDao;
import com.example.demo.Html.model.ItemHomePageMore;
import com.example.demo.Html.model.ShopInfo;
import com.example.demo.Html.service.ItemServiceImpl;
import com.example.demo.Html.service.ShopInfoServiceImpl;
import com.example.demo.Html.service.UserInfoServiceImpl;
import net.bytebuddy.asm.Advice;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
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
public class AdminController {
    @Autowired
    ShopInfoDao shopInfoDao;

    final
    ShopInfoServiceImpl shopInfoService;
    final
    ItemServiceImpl itemService;
    final
    UserInfoServiceImpl userInfoService;

    public AdminController(ShopInfoServiceImpl shopInfoService, ItemServiceImpl itemService, UserInfoServiceImpl userInfoService) {
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
    public Map<String, List<ShopInfo>> findAallShopInfo() {
        Map<String, List<ShopInfo>> map = new HashMap<>();
        map.put("ShopInfo", shopInfoService.findAllShopInfo());
        return map;
    }

    /**
     * 获取店铺内商品列表
     *
     * @param shop_id 店铺id
     * @return 商品列表
     */
    @RequestMapping(value = "/findItemInfoByShopId")
    public Map<String, Object> findItemInfoByShopId(Integer shop_id) {
        Map<String, Object> map = new HashMap<>();
        if (shop_id == null ) {
            map.put("500","参数有误");
            return map;
        }
        map.put("itemInfo", itemService.findByItemInfoByShopId(shop_id));
        String shop_address = shopInfoService.findShopInfoByShopId(shop_id).getShopAddress();
        map.put("userInfo", userInfoService.findUserInfoByUserAddress(shop_address));
        return map;
    }

    /**
     * 添加寝室
     *
     * @param shop_address 寝室号
     * @return 200/500
     */
    @Transactional(rollbackOn = Throwable.class)
    @RequestMapping(value = "/addShop")
    public Map<String, Object> addShop(String shop_address) {
        Map<String, Object> map = new HashMap<>();
        if (shopInfoService.findExistShopByShopAddress(shop_address)) {
            map.put("500", "寝室号已经存在");
            return map;
        }
        if (shop_address == null || shop_address.length() < 1) {
            map.put("500","参数有误");
            return map;
        }

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopAddress(shop_address);
        shopInfo.setTotalSales("0");
        shopInfo.setUserNum(0);
        shopInfoDao.save(shopInfo);
        map.put("200", "寝室添加成功");
        return map;
    }
}
