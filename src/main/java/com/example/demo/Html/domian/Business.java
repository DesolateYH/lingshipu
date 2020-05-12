package com.example.demo.Html.domian;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：Q
 * @date ：Created in 2019/3/27 22:57
 * @description：${description}
 */
@Entity
@Data
@Table(name = "business")
public class Business {
    private int businessId;
    private String businessName;
    @Id
    private String businessLoginname;
    private String businessPassword;
    private String businessTouxiang;
    private String businessJurisdiction;
}
