package com.example.demo.html.service;

import com.example.demo.html.domian.po.LspOrderInfoPO;
import com.example.demo.html.repository.LspOrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: lingshipu
 * @description: 订单
 * @author: QWS
 * @create: 2020-05-13 02:56
 */
@Service
public class LspOrderInfoServiceImpl {
    @Autowired
    LspOrderInfoRepository lspOrderInfoRepository;

    /**
     * 生成订单
     *
     * @param lspOrderInfoPO 对象
     */
    public void saveOrderInfo(LspOrderInfoPO lspOrderInfoPO) {
        lspOrderInfoRepository.save(lspOrderInfoPO);
    }

    /**
     * 获取订单信息
     * @param openid openid
     * @return 1
     */
    public List<LspOrderInfoPO> getOrderInfo(String openid){
        return lspOrderInfoRepository.findByVxUserOpenidAndOrderState(openid,1);
    }

    /**
     * 获取订单信息
     * @param outTradeNo 订单号
     * @return 1
     */
    public LspOrderInfoPO findByOrderOutTradeNo(String outTradeNo){
        return lspOrderInfoRepository.findByOrderOutTradeNo(outTradeNo);
    }

    /**
     * 删除订单
     * @param state 订单状态
     */
    @Transactional
    public void deleteByOrderState(int state){
        lspOrderInfoRepository.deleteByOrderState(state);
    }
}

