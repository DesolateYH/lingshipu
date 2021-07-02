package com.example.demo.html.service;

import com.example.demo.html.repository.PropertyaDao;
import com.example.demo.html.domian.Propertya;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PropertyaServiceImpl {
    @Autowired
    PropertyaDao propertyaDao;

    //登陆
    public Propertya getByPropertyaLoginnameAndPropertyaPassword(String propertyaLoginname, String propertyaPassword) {
        return propertyaDao.getByPropertyaLoginnameAndPropertyaPassword(propertyaLoginname,propertyaPassword);
    }


}
