package com.example.demo.newlsp.domain.vo;

import com.example.demo.html.domian.po.ItemAllModel;
import com.example.demo.html.domian.vo.SortPageItemVO;
import lombok.Data;

import java.util.List;

/**
 * @program: lingshipu
 * @description:
 * @author: QWS
 * @create: 2021-05-15 17:24
 */
@Data
public class SortPageSotckVO {
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
    List<ItemAllModel> sortPageItem;
}
