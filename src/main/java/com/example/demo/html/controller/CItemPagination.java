package com.example.demo.html.controller;

import com.example.demo.html.repository.BusinessDao;
import com.example.demo.html.repository.ItemPaginationDao;
import com.example.demo.html.domian.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author ：Q
 * @date ：Created in 2019/4/10 23:56
 * @description：${description}
 */
@Controller
@CrossOrigin
public class CItemPagination {
    @Autowired
    ItemPaginationDao itemPaginationDao;
    @Autowired
    BusinessDao businessDao;

    @RequestMapping(value = "/testpay")
    public String test(){
        return "pay";
    }

    //商品分页
    @RequestMapping(value = "/getItemPagination", method = RequestMethod.GET)
    public String getEntryByParams(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", defaultValue = "5") Integer size, HttpSession httpSession) {
        Business business = businessDao.getByBusinessLoginname(httpSession.getAttribute("user_name"));
        try {
            if (!"超级管理员".equals(business.getBusinessJurisdiction())) {
                return "ErrorInsufficientAuthority";
            }
        } catch (Exception exception) {
            return "ErrorLoginAgain";
        }
        try {
            Sort sort = new Sort(Sort.Direction.ASC, "itemId");
            Pageable pageable = new PageRequest(page, size, sort);
            Page item = itemPaginationDao.findAll(pageable);
            model.addAttribute("ItemPaginationList", item);
//        model.addAttribute("pageNumber",item.getNumber());
            model.addAttribute("business_name", business.getBusinessName());
            model.addAttribute("business_jurisdiction", business.getBusinessJurisdiction());
            return "AdminItemList";
        } catch (Exception exception) {
            return "ErrorLoginAgain";
        }
    }
   /* @RequestMapping(value = "AsdModel", method=RequestMethod.GET)
    public Page<ItemPagination> getEntryByPageable(@PageableDefault(value = 15, sort = { "itemId" }, direction = Sort.Direction.DESC)
                                                 Pageable pageable) {
        return itemPaginationDao.findAll(pageable);
    }*/
}
