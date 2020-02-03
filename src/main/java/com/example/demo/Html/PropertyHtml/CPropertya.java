package com.example.demo.Html.PropertyHtml;

import com.example.demo.Html.dao.Propertya_InfoDao;
import com.example.demo.Html.model.Propertya_Info;
import com.example.demo.Html.service.PropertyaServiceImpl;
import com.example.demo.Html.model.Propertya;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Controller
public class CPropertya {
    @Autowired
    PropertyaServiceImpl PropertyaServiceImpl;
    @Autowired
    Propertya_InfoDao propertya_infoDao;

    @GetMapping(value = "/LoginProperty")
    public String LoginProperty(){
        return "LoginProperty";
    }


    //登陆
    @PostMapping(value = "/doLoginForProperty")
    public String doLogin(Model model, Propertya propertya, HttpSession httpSession) {
        Propertya propertya1 = PropertyaServiceImpl.getByPropertyaLoginnameAndPropertyaPassword(propertya.getPropertyaLoginname(), propertya.getPropertyaPassword());
        if(propertya1 == null){
            model.addAttribute("msg", "密码错误");
            return "LoginProperty";
        }else{
            //返回所有公告
            httpSession.setAttribute("user_name", propertya.getPropertyaLoginname());
            List<Propertya_Info>propertya_infos = propertya_infoDao.findAll();
            model.addAttribute("propertya_infos",propertya_infos);

            return "PropertyIndex";
        }
    }



}
