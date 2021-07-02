package com.example.demo.html.repository;

import com.example.demo.html.domian.po.LspOrderInfoPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 订单
 * @author: QWS
 * @create: 2020-05-13 02:56
 */
@Repository
public interface LspOrderInfoRepository extends JpaRepository<LspOrderInfoPO,Integer> {
    /**
     * 获取订单信息
     * @param i openid
     * @return 1
     */
    List<LspOrderInfoPO> findByVxUserOpenidAndOrderState(String i,int s);




    /**
     * 删除订单
     * @param orderState 描述
     */
    @Modifying
    @Transactional
    void deleteByOrderState(int orderState);

    /**
     * 获取订单信息
     * @param outTradeNo 订单号
     * @return 1
     */
    LspOrderInfoPO findByOrderOutTradeNo(String outTradeNo);

    /**
     * 获取订单信息
     * @param shopAddress 寝室号
     * @return 1
     */
    List<LspOrderInfoPO> findByShopAddress(String shopAddress);

    /**
     * 模糊查询寝室号
     * @param shopAddress 寝室号
     * @return 1
     */
    List<LspOrderInfoPO> findByShopAddressLike(String shopAddress, Pageable pageable);






}
