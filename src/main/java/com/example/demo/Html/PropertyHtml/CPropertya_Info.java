package com.example.demo.Html.PropertyHtml;

import com.example.demo.Html.dao.Propertya_InfoDao;
import com.example.demo.Html.model.Propertya_Info;
import com.example.demo.Html.service.Propertya_InfoServiceImpl;
import org.bouncycastle.math.ec.ScaleYPointMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/3/24 17:35
 * @description：${description}
 */

@Controller
public class CPropertya_Info {
    @Autowired
    Propertya_InfoServiceImpl propertya_infoService;
    @Autowired
    Propertya_InfoDao propertya_infoDao;
    //发布公告
    @PostMapping(value = "/submitPropertyInfo")
    public String submitPropertyInfo(Model model, String property_info_tittle, String property_info, HttpSession httpSession) throws IOException {
        boolean flag = propertya_infoService.submitPropertyInfo(property_info_tittle, property_info,httpSession);
        if(flag) {
            List<Propertya_Info> propertya_infos = propertya_infoDao.findAll();
            model.addAttribute("propertya_infos",propertya_infos);
            model.addAttribute("msg", "提交成功");
        }else {
            List<Propertya_Info> propertya_infos = propertya_infoDao.findAll();
            model.addAttribute("propertya_infos",propertya_infos);
            model.addAttribute("msg", "提交失败");
        }
        return "PropertyIndex";
    }

    //删除公告
    @PostMapping(value = "/deleteInfo")
    public String deleteInfo(Model model, HttpServletRequest request) throws IOException {

        String[] property_info_tittle = request.getParameterValues("select");
        String user_name = request.getParameter("user_name");
        for (String s : property_info_tittle) {
            propertya_infoDao.getByPropertyInfoTittleAndPropertyInfoUser(s,user_name);
        }

        boolean flag;
        if(property_info_tittle == null || user_name == null){
            List<Propertya_Info> propertya_infos = propertya_infoDao.findAll();
            model.addAttribute("propertya_infos",propertya_infos);
            model.addAttribute("deletemsg", "删除失败");
            return "PropertyIndex";
        }
        System.out.println(user_name);
        System.out.println(Arrays.toString(property_info_tittle));
        flag = propertya_infoService.deleteByPropertyInfoTittle(property_info_tittle,user_name);
        if(flag) {
            List<Propertya_Info> propertya_infos = propertya_infoDao.findAll();
            model.addAttribute("propertya_infos",propertya_infos);
            model.addAttribute("deletemsg", "删除成功");

        }else {
            List<Propertya_Info> propertya_infos = propertya_infoDao.findAll();
            model.addAttribute("propertya_infos",propertya_infos);
            model.addAttribute("deletemsg", "删除失败");
        }
        return "PropertyIndex";
    }
}
