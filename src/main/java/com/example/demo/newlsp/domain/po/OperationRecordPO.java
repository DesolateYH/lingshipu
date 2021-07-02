package com.example.demo.newlsp.domain.po;

import com.example.demo.html.domian.po.ItemAllModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: lingshipu
 * @description: 操作记录
 * @author: QWS
 * @create: 2021-02-19 20:24
 */
@Data
@Entity
@Table(name = "operationRecord")
@EntityListeners(AuditingEntityListener.class)
public class OperationRecordPO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int operationRecordId;
    /**
     * 操作人员id
     */
    String operationRecordAdminId;

    /**
     * 操作人员姓名
     */
    String operationRecordAdminName;
    /**
     * 操作唯一序列号
     */
    String operationRecordSerial;

//    /**
//     * 操作货单
//     */
//    @OneToMany(targetEntity = ItemAllModel.class)
//    List<ItemAllModel> operationRecordContext;

    /**
     * 操作时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    Timestamp operationRecordTime;

    /**
     * 操作类型
     */
    String operationRecordType;

}
