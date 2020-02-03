package com.example.demo.Html.Test;/*
package com.example.demo.Html.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

*/
/**
 * @author ：Q
 * @date ：Created in 2019/4/10 23:39
 * @description：${description}
 *//*

@Controller
public class BookController {
    @Autowired
    BookQueryService bookQueryService;

    @RequestMapping("/findBookNoQuery")
    public String findBookNoQuery(ModelMap modelMap, @RequestParam(value = "https://my.oschina.net/wangxincj/blog/page", defaultValue = "https://my.oschina.net/wangxincj/blog/0") Integer page,
                                  @RequestParam(value = "https://my.oschina.net/wangxincj/blog/size", defaultValue = "https://my.oschina.net/wangxincj/blog/5") Integer size){
        Page<Book> datas = bookQueryService.findBookNoCriteria(page, size);
        modelMap.addAttribute("datas", datas);
        return "index1";
    }

    @RequestMapping(value = "https://my.oschina.net/findBookQuery",method = {RequestMethod.GET,RequestMethod.POST})
    public String findBookQuery(ModelMap modelMap, @RequestParam(value = "https://my.oschina.net/wangxincj/blog/page", defaultValue = "https://my.oschina.net/wangxincj/blog/0") Integer page,
                                @RequestParam(value = "https://my.oschina.net/wangxincj/blog/size", defaultValue = "https://my.oschina.net/wangxincj/blog/5") Integer size, BookQuery bookQuery){
        Page<Book> datas = bookQueryService.findBookCriteria(page, size,bookQuery);
        modelMap.addAttribute("datas", datas);
        return "index2";
    }
}
*/
