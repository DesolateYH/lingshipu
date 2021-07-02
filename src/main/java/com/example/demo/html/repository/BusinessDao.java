package com.example.demo.html.repository;

import com.example.demo.html.domian.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/3/27 22:56
 * @description：${description}
 */
@Repository
public interface BusinessDao extends JpaRepository<Business,Integer> {
    Business getByBusinessLoginnameAndBusinessPassword(String businessLoginname, String businessPassword);
    List<Business> findAllByBusinessJurisdiction(String business_jurisdiction);
    Business getByBusinessLoginname(Object businessLoginname);
}
