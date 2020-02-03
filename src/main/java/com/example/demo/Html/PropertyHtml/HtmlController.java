package com.example.demo.Html.PropertyHtml;

import com.example.demo.Html.service.SendEmail;
import com.example.demo.Html.service.SendEmail2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HtmlController {
    @Autowired
    SendEmail sendEmail;
    @Autowired
    SendEmail2 sendEmail2;
    @PostMapping(value = "/windcosp/getEmailInfo")
    public String getEmailInfo(String name, String email, String message, Model model){
        if("".equals(name) || "".equals(email) || "".equals(message)){
            model.addAttribute("msg","发送失败，名字、联系方式、反馈的消息不能为空");
            return "windCospSuccess";
        }
        try {
/*            sendEmail.main(name,email,message);*/
            sendEmail2.sendEmil(name,email,message);
            model.addAttribute("msg","发送成功");
            return "windCospSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg","发送失败");
            return "windCospSuccess";
        }
    }
    @GetMapping(value = "/windcops/returnIndex")
    public String returnIndex(){
        String redirectUrl = "http://chinaqwe.top";
        return "redirect:" + redirectUrl;
    }


}
