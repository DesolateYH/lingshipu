package com.example.demo.Html.PropertyHtml;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：Q
 * @date ：Created in 2019/4/28 23:57
 * @description：
 */
@CrossOrigin
@Controller
public class CHtml {
    @GetMapping(value = "/AddItem")
    public String AddItemHtml(){
        return "AddItem";
    }
}
