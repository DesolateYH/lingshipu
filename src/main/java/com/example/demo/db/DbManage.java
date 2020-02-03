package com.example.demo.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DbManage {
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://116.62.161.99:3306/lingshipu");
        ds.setPassword("qwe123");
        ds.setUsername("javaee");
        return ds;
    }
}
