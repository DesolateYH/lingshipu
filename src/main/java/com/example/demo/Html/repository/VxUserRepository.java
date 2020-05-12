package com.example.demo.Html.repository;

import com.example.demo.Html.domian.po.VxUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: lingshipu
 * @description:
 * @author: QWS
 * @create: 2020-05-12 04:43
 */
public interface VxUserRepository extends JpaRepository<VxUserModel,String> {
    VxUserModel findBySessionKey(String i);
}
