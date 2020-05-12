package com.example.demo.Html.domian.vo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * @program: lingshipu
 * @description: 分类
 * @author: QWS
 * @create: 2020-05-12 05:40
 */
@Data
public class SortPageVO {
    /**
     * id
     */
    int sortId;
    /**
     * 名称
     */
    String sortName;
    /**
     * 图片地址
     */
    String sortPhotoAddress;

    /**
     * 子类商品
     */
    List<SortPageItemVO> sortPageItem;
}
