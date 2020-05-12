package com.example.demo.Html.repository;

import com.example.demo.Html.domian.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/3/27 22:56
 * @description：${description}
 */
public interface BusinessDao extends JpaRepository<Business,Integer> {
    Business getByBusinessLoginnameAndBusinessPassword(String businessLoginname, String businessPassword);
    List<Business> findAllByBusinessJurisdiction(String business_jurisdiction);
    Business getByBusinessLoginname(Object businessLoginname);
}
