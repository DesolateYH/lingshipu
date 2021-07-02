package com.example.demo.html.domian;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author ：Q
 * @date ：Created in 2019/3/23 23:26
 * @description：${description}
 */

@Entity
@Data
@Table(name = "property_info")
public class Propertya_Info {

    String property_info_id;
    @Id
    @Column(name = "property_info_tittle")
    String propertyInfoTittle;
    String property_info;
    Timestamp property_info_time;
    @Column(name = "property_info_user")
    String propertyInfoUser;
    String property_info_user_touxiang;
    @Column(name = "property_info_replynumber")
    String property_info_replyNumber;
}
