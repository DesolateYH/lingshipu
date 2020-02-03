package com.example.demo.Html.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ：Q
 * @date ：Created in 2019/4/1 15:44
 * @description：${description}
 */
@Entity
@Data
@Table(name="vx_homepage_more")
public class ItemHomePageMore{
    @Id
    private int itemId;
    private String itemName;
    private String itemPrice;

}
