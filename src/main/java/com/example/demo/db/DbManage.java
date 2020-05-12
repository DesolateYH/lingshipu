package com.example.demo.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DbManage {
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://lsp.chinaqwe.top:3306/lingshipu?useSSL=false");
        ds.setUsername("root");
        ds.setPassword("qwe123");
        return ds;
    }
}
