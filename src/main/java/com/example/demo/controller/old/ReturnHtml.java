package com.example.demo.controller.old;/*package com.example.demo.controller;


import com.example.demo.Logic.login.LVx_User;
import com.example.demo.entity.login.Vx_User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ReturnHtml {
    @RequestMapping("/main")
    public String showMain(HttpServletRequest request, Model model){
        String stu_code = "";
        System.out.println(request.getSession().getAttribute("stu_code"));
        if (request.getSession().getAttribute("stu_code") != null)
            stu_code = request.getSession().getAttribute("stu_code").toString();
        model.addAttribute("stu_code", stu_code);

        return  "index";
    }

    @RequestMapping("/abc")
    public String queryStudent(Model model,HttpServletRequest request){
        List<Vx_User> vxusers = null;
        LVx_User vxuser = new LVx_User();
        String sqlTxt = "select * from vx_user";
        try {
            vxusers = vxuser.queryCourse(sqlTxt);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        model.addAttribute("vxusers", vxusers);
        return  "abc";
    }

}
*/