package com.example.demo.html.domian.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: lingshipu
 * @description: 管理员信息表
 * @author: QWS
 * @create: 2020-03-05 18:59
 */

@Entity
@Data
@Table(name = "admin_info")
public class AdminInfoModel {
    /**
     * 用户名
     */
    @Id
    @Column(columnDefinition = "varchar(20) default 'LTD' not null")
    String userId;
    /**
     * 密码
     */
    String password;
    /**
     *  * 所能管理的寝室id看，超级管理员默认值为-1
     */
    String permissions;
    /**
     * 昵称
     */
    String nickName;



}
