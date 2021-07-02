package com.example.demo.newlsp.controller;

import com.example.demo.CheckAdminInterceptor;
import com.example.demo.CheckParamsInterceptor;
import com.example.demo.html.ToolUntil;
import com.example.demo.html.domian.Item;
import com.example.demo.html.domian.po.ItemAllModel;
import com.example.demo.html.domian.po.ItemHomePageMore;
import com.example.demo.html.domian.po.SortPagePO;
import com.example.demo.html.domian.po.UserInfoModel;
import com.example.demo.html.domian.vo.Msg;
import com.example.demo.html.domian.vo.SortPageItemVO;
import com.example.demo.html.domian.vo.SortPageVO;
import com.example.demo.html.repository.ItemAllDao;
import com.example.demo.html.repository.ItemHomePageMoreDao;
import com.example.demo.html.repository.SortPageRepository;
import com.example.demo.html.repository.UserInfoDao;
import com.example.demo.html.service.LspVxUserServiceImpl;
import com.example.demo.html.service.UserInfoServiceImpl;
import com.example.demo.newlsp.domain.po.OperationRecordPO;
import com.example.demo.newlsp.domain.vo.SortPageSotckVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.CheckParamsInterceptor.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


/**
 * @program: lingshipu
 * @description: 库存
 * @author: QWS
 * @create: 2021-02-19 20:27
 */
@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    ItemAllDao itemAllDao;
    @Autowired
    ItemHomePageMoreDao itemHomePageMoreDao;
    @Autowired
    OperationRecordController operationRecordController;
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    SortPageRepository sortPageRepository;

    /**
     * 添加库存
     *
     * @param itemName     商品名称
     * @param itemPrice    价格
     * @param itemNum      数量
     * @param sort_id      分类id
     * @param access_token token
     * @param file         图片
     * @return 1
     */
    @RequestMapping(value = "/addStock")
    public Msg addStock(@ParamsNotNull String itemName,
                        @ParamsNotNull String itemPrice,
                        @ParamsNotNull Integer itemNum,
                        @ParamsNotNull Integer sort_id,
                        @ParamsNotNull @CheckAdminInterceptor.CheckAdmin String access_token,
                      MultipartFile file) throws Exception {
        ItemAllModel itemAllModel = new ItemAllModel();

        String openid = lspVxUserService.getOpenidByToken(access_token);
        if (openid == null || openid.length() < 1) {
            return Msg.statu401();
        }
        UserInfoModel userInfoModel = userInfoService.findByOpenid(openid);


        if ( itemNum < 0) {
            return Msg.statu400().add("info", "参数超出范围");
        }
        String url = ToolUntil.uploadFile(file);
        if ("error".equals(url)) {
            return Msg.statu403().add("error", "图片上传失败");
        }
        itemAllModel.setItemName(itemName);
        itemAllModel.setItemPrice(itemPrice);
        itemAllModel.setInventoryBalance(itemNum);
        itemAllModel.setStockCurrentAll(0);
        itemAllModel.setItemSortId(sort_id);
        itemAllModel.setSalesVolumeAll(0);
        itemAllModel.setItemPicUrl(url);
        itemAllDao.saveAndFlush(itemAllModel);

        List<ItemAllModel> itemAllModels = new ArrayList<>();
        itemAllModels.add(itemAllModel);



        operationRecordController.saveOpeartionRecord(userInfoModel.getAdminId(), itemAllModels, "添加到库存");

        return Msg.statu200();
    }

    /**
     * 删除库存
     *
     * @param itemId 商品id
     * @return 203拦截器判断参数为空、400参数超出范围、403商品id不存在、403用户盒子中有剩余商品
     */
    @RequestMapping(value = "/deleteStock")
    @Transactional
    public Msg deleteStock(@ParamsNotNull Integer itemId) {
        if (itemId < 0) {
            return Msg.statu400().add("info", "参数超出范围");
        }
        if (itemAllDao.findByItemId(itemId) == null) {
            return Msg.statu403().add("info", "商品id不存在");
        }
        if (itemHomePageMoreDao.findByItemId(itemId).size() != 0) {
            return Msg.statu403().add("info", "用户盒子中有剩余商品");
        }
        ItemAllModel itemAllModel = itemAllDao.findByItemId(itemId);
        try {
            itemAllDao.deleteByItemId(itemId);

            List<ItemAllModel> itemAllModels = new ArrayList<>();
            itemAllModels.add(itemAllModel);
            operationRecordController.saveOpeartionRecord("待添加", itemAllModels, "删除库存");

            return Msg.statu200();
        } catch (Exception e) {
            return Msg.statu403().add("error", e.getMessage());
        }
    }

    /**
     * 更新库存（为空则不更新）
     *
     * @param itemId           id（不能为空）
     * @param itemName         名称
     * @param itemPrice        价格
     * @param stockCurrentAll  总已投放库存
     * @param salesVolumeAll   总销量
     * @param inventoryBalance 总未投放库存
     * @return 数字类型没有做负数判断
     */
    @RequestMapping(value = "/updateStock")
    public Msg updateStock(@ParamsNotNull Integer itemId, String itemName, String itemPrice,
                           Integer inventoryBalance, Integer stockCurrentAll, Integer salesVolumeAll) {


        if (itemAllDao.findByItemId(itemId) == null) {
            return Msg.statu403().add("info", "商品id不存在");
        }

        ItemAllModel itemAllModel = itemAllDao.findByItemId(itemId);

        if (itemName != null)
            itemAllModel.setItemName(itemName);
        if (itemPrice != null)
            itemAllModel.setItemPrice(itemPrice);
        if (inventoryBalance != null)
            itemAllModel.setInventoryBalance(inventoryBalance);
        if (stockCurrentAll != null)
            itemAllModel.setStockCurrentAll(stockCurrentAll);
        if (salesVolumeAll != null)
            itemAllModel.setSalesVolumeAll(salesVolumeAll);
        itemAllModel.setItemPicUrl("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3791918726,2864900975&fm=26&gp=0.jpg");
        itemAllDao.save(itemAllModel);
        return Msg.statu200();
    }

    /**
     * 获取全部库存商品（带分类）
     * @param access_token token
     * @return 1
     */
    @RequestMapping(value = "/getAllWithSort")
    public Msg getAllWithSort(@ParamsNotNull @CheckAdminInterceptor.CheckAdmin String access_token){
        List<ItemAllModel> itemAllModels = itemAllDao.findAll();
        List<SortPagePO> sortPagePOS = sortPageRepository.findAll();
        List<SortPageSotckVO> sortPageSotckVOS = new ArrayList<>();
        for(SortPagePO sortPagePO : sortPagePOS){
            SortPageSotckVO sortPageSotckVO = new SortPageSotckVO();
            sortPageSotckVO.setSortId(sortPagePO.getSortId());
            sortPageSotckVO.setSortName(sortPagePO.getSortName());
            sortPageSotckVO.setSortPhotoAddress(sortPagePO.getSortPhotoAddress());


            int sortId = sortPagePO.getSortId();
            List<ItemAllModel> itemAllModels1 = new ArrayList<>();
            for(ItemAllModel itemAllModel : itemAllModels){
                if(itemAllModel.getItemSortId()==sortId){
                    itemAllModels1.add(itemAllModel);
                }
            }
            sortPageSotckVO.setSortPageItem(itemAllModels1);
            sortPageSotckVOS.add(sortPageSotckVO);
        }
        return Msg.statu200().add("info",sortPageSotckVOS);
    }

}
