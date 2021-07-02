package com.example.demo.html.domian;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "property")
public class Propertya {
    @Column(name="propertya_loginname")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String propertyaLoginname;
    String propertyaPassword;
    String property_info_user_touxiang;


}
