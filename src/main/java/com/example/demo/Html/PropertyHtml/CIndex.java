package com.example.demo.Html.PropertyHtml;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：Q
 * @date ：Created in 2019/5/6 14:51
 * @description：
 */
@Controller
public class CIndex {
    @GetMapping(value = "index")
    public String returnindex(){
        return "index";
    }
}
