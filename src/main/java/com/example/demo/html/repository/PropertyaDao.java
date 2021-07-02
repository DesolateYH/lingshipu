package com.example.demo.html.repository;


import com.example.demo.html.domian.Propertya;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PropertyaDao extends JpaRepository<Propertya,Integer> {
     Propertya getByPropertyaLoginnameAndPropertyaPassword(String propertyaLoginname, String propertyaPassword);

     Propertya getByPropertyaLoginname(String propertyaLoginname);

}

