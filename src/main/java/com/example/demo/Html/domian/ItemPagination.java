package com.example.demo.Html.domian;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：Q
 * @date ：Created in 2019/3/28 0:08
 * @description：${description}
 */
@Entity
@Data
@Table(name = "item_detailed")
public class ItemPagination{
    @Id
    private String itemId;
    private String changtu;
    private String shengchangriqi;
    private String baozhiqi;
    private String tupian1;
    private String tupian2;
    private String tupian3;


}

