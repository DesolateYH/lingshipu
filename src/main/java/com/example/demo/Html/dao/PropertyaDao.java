package com.example.demo.Html.dao;


import com.example.demo.Html.MyModel;
import com.example.demo.Html.model.Propertya;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PropertyaDao extends JpaRepository<Propertya,Integer> {
     Propertya getByPropertyaLoginnameAndPropertyaPassword(String propertyaLoginname, String propertyaPassword);

     Propertya getByPropertyaLoginname(String propertyaLoginname);

}

