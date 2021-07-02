package com.example.demo.newlsp.domain.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: lingshipu
 * @description: 公告
 * @author: QWS
 * @create: 2021-03-14 01:17
 */
@Entity
@Data
@Table(name = "notic")
public class NoticePO {
    /**
     * 公告
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int noticId;
    /**
     * 公告内容
     */
    String noticInfo;
}
