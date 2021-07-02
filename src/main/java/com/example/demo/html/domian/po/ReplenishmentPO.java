package com.example.demo.html.domian.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @program: lingshipu
 * @description: 补货清单
 * @author: QWS
 * @create: 2020-06-03 01:02
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name="Replenishment")
public class ReplenishmentPO {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int replenishmentItemId;
    /**
     * 寝室地址
     */
    String replenishmentShopAddress;

    /**
     * 价格
     */
    String replenishmentAllPrice;

    /**
     * 信息
     */
    String replenishmentInfo;

    /**
     * 创建时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @CreatedDate
    Timestamp CreatTime;

    /**
     * 是否完成
     */
    int isFinish;

    /**
     * 状态码
     */
    @Column(columnDefinition="int default 0")
    int replenishmentStatusCode;

    /**
     * 是否锁定
     */
    @Column(columnDefinition = "int(1) default 0 not null")
    int replenishmentIsLock;
}
