package com.example.demo.Html.PropertyHtml;

import com.example.demo.Html.dao.BusinessDao;
import com.example.demo.Html.dao.ItemDao;
import com.example.demo.Html.model.Business;
import com.example.demo.Html.service.BusinessServiceImpl;
import com.example.demo.Html.service.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/4/1 15:32
 * @description：${description}
 */
@Controller
@CrossOrigin
public class CItem {
    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    BusinessDao businessDao;

    //管理员后台获取商品列表
    @GetMapping(value = "AdminGetItemList")
    public String adminGetItemList(Model model, HttpSession httpSession){
        Business business = businessDao.getByBusinessLoginname(httpSession.getAttribute("user_name"));
        try {
            if (!"超级管理员".equals(business.getBusinessJurisdiction())){
                return "ErrorInsufficientAuthority";
            }
        }catch (Exception exception){
            return "ErrorLoginAgain";
        }
        try {
            model.addAttribute("business_name",business.getBusinessName());
            model.addAttribute("business_jurisdiction",business.getBusinessJurisdiction());
            model.addAttribute("ItemList",itemService.getItemAndHomePageMoreRelation());
            return "AdminItemList";
        }catch (Exception exception){
            return "ErrorLoginAgain";
        }
    }

}
