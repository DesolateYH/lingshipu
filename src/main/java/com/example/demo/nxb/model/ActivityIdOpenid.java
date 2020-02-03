package com.example.demo.nxb.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="nxb_activity_openid")
public class ActivityIdOpenid {
    @Id
    int unnecessaryId;
    int activityId;
    String vxUserOpenid;
}
