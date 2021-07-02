package com.example.demo.html.domian.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: lingshipu
 * @description: 分类
 * @author: QWS
 * @create: 2020-05-12 05:34
 */

@Entity
@Data
@Table(name = "sort_page")
public class SortPagePO {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int sortId;
    /**
     * 名称
     */
    String sortName;
    /**
     * 图片地址
     */
    String sortPhotoAddress;


}
