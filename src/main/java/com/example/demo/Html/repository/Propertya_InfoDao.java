package com.example.demo.Html.repository;

import com.example.demo.Html.domian.Propertya_Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：Q
 * @date ：Created in 2019/3/23 23:23
 * @description：${description}
 */
public interface Propertya_InfoDao extends JpaRepository<Propertya_Info,Integer> {
    List<Propertya_Info> findAll();

    Propertya_Info getByPropertyInfoTittleAndPropertyInfoUser(String user_name, String property_info_tittle);

    void deleteByPropertyInfoTittleAndPropertyInfoUser(String property_info_tittle, String user_name);
}
