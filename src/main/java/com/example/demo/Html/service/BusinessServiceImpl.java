package com.example.demo.Html.service;

import com.example.demo.Html.repository.BusinessDao;
import com.example.demo.Html.domian.Business;
import com.example.demo.db.DbManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：Q
 * @date ：Created in 2019/3/27 23:07
 * @description：${description}
 */
@Service
public class BusinessServiceImpl {
    @Autowired
    BusinessDao businessDao;
    //商品管理系统登陆
    public Business doLoginForBusiness(Business business){
        return businessDao.getByBusinessLoginnameAndBusinessPassword(business.getBusinessLoginname(),business.getBusinessPassword());
    }
    //搜寻供货商数量
    public int seachBusinessNumber() {
        List<Business> list = businessDao.findAllByBusinessJurisdiction("供货商");
        return list.size();
    }
    //查询商品总数
    public int seachItemNumber() {
        String sqlTxt="select count(*) as itemNumber from item_detailed";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        int sum = 0;
        for(Map<String, Object> map : list){
            sum = Integer.parseInt(map.get("itemNumber").toString());
        }
        return  sum;
    }
}
