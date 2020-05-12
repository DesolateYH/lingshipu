package com.example.demo.Html.domian.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: lingshipu
 * @description:
 * @author: QWS
 * @create: 2020-05-12 04:42
 */

@Data
@Table(name  = "vx_user")
@Entity
public class VxUserModel {
    @Id
    String vxUserOpenid;
    String vxUserName;
    String vxUserMoney;
    String sessionKey;
    int shopId;
}
