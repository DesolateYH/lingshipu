package com.example.demo.html.domian;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：Q
 * @date ：Created in 2019/3/27 22:57
 * @description：${description}
 */
@Entity
@Data
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int businessId;
    private String businessName;


    private String businessLoginname;
    private String businessPassword;
    private String businessTouxiang;
    private String businessJurisdiction;
}
