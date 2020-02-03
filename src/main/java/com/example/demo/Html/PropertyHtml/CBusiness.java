package com.example.demo.Html.PropertyHtml;

import com.example.demo.Html.ToolUntil;
import com.example.demo.Html.dao.BusinessDao;
import com.example.demo.Html.model.Business;
import com.example.demo.Html.service.BusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author ：Q
 * @date ：Created in 2019/3/27 22:40
 * @description：${description}
 */
@Controller
@ComponentScan
public class CBusiness {
    @Autowired
    BusinessServiceImpl businessService;
    @Autowired
    BusinessDao businessDao;


    @GetMapping(value = "/LoginBusiness")
    public String LoginBusiness() {
        return "LoginBusiness";
    }

    //登陆
    @PostMapping(value = "/doLoginForBusiness")
    public String doLogin(Model model, Business business1, HttpSession httpSession) {
        Business business = businessService.doLoginForBusiness(business1);
        if (business == null) {
            model.addAttribute("msg", "密码错误");
            return "LoginBusiness";
        } else if("超级管理员".equals(business.getBusinessJurisdiction())){
            //返回所有公告
            httpSession.setAttribute("user_name", business.getBusinessLoginname());
            model.addAttribute("business_name",business.getBusinessName());
            model.addAttribute("business_jurisdiction",business.getBusinessJurisdiction());
            model.addAttribute("business_number",businessService.seachBusinessNumber());
            model.addAttribute("item_number",businessService.seachItemNumber());
            model.addAttribute("SystemName", ToolUntil.getOsName());
            model.addAttribute("FreeMemory", ToolUntil.getFreePhysicalMemorySize());
            model.addAttribute("ThreadLoad",ToolUntil.getProcessCpuLoad());
            model.addAttribute("CPULoad",ToolUntil.getSystemCpuLoad());
            return "AdminIndex";
        }
        return "BusinessIndex";
    }
    //管理员获取商品列表
    @GetMapping(value = "/AdminItemList")
    public String returnAdminItemList(Model model, HttpSession httpSession){
        Business business = businessDao.getByBusinessLoginname(httpSession.getAttribute("user_name"));
        try {
            model.addAttribute("business_name",business.getBusinessName());
            model.addAttribute("business_jurisdiction",business.getBusinessJurisdiction());
            return "AdminItemList";
        }catch (Exception exception){
            return "ErrorLoginAgain";
        }
    }

    //管理员后台首页
    @GetMapping(value = "/AdminHome")
    public String returnAdminHome(Model model,HttpSession httpSession){
        Business business = businessDao.getByBusinessLoginname(httpSession.getAttribute("user_name"));
        try {
            model.addAttribute("business_name", business.getBusinessName());
            model.addAttribute("business_jurisdiction", business.getBusinessJurisdiction());
            model.addAttribute("business_number", businessService.seachBusinessNumber());
            model.addAttribute("item_number", businessService.seachItemNumber());
            model.addAttribute("SystemName", ToolUntil.getOsName());
            model.addAttribute("FreeMemory", ToolUntil.getFreePhysicalMemorySize());
            model.addAttribute("ThreadLoad", ToolUntil.getProcessCpuLoad());
            model.addAttribute("CPULoad", ToolUntil.getSystemCpuLoad());
            return "AdminIndex";
        }catch (Exception exception){
            return "ErrorLoginAgain";
        }
    }

    //退出后台
    @GetMapping(value = "/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("user_name");
        return "redirect:LoginBusiness";
    }
}
