package com.example.demo.newlsp.domain.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @program: lingshipu
 * @description: 盘货记录
 * @author: QWS
 * @create: 2021-04-24 21:54
 */

@Entity
@Data
@Table(name = "inventory_record")
public class InventoryRecordPO {
    /**
     * id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int inventoryRecordId;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    Timestamp inventoryRecordCreatedTime;
    /**
     * 寝室id
     */
    int inventoryRecordShopId;
    /**
     * 寝室名
     */
    String inventoryRecordShopName;

    /**
     * 是否缺失
     */
    int inventoryRecordIsDefect;
    /**
     * 缺失json
     */
    String inventoryRecordDefectJson;
    /**
     * 用户id
     */
    int inventoryRecordUserId;
    /**
     * 用户姓名
     */
    String inventoryRecordUserName;
    /**
     * 用户openid
     */
    String inventoryRecordOpenId;
}
