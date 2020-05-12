package com.example.demo.Html.service;

import com.example.demo.Html.SessionUtil;
import com.example.demo.Html.ToolUntil;
import com.example.demo.Html.domian.po.LspOrderInfoPO;
import com.example.demo.Html.repository.LspOrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean saveOrderInfo(LspOrderInfoPO lspOrderInfoPO) {
        if (lspOrderInfoPO == null) {
            return false;
        }
        lspOrderInfoPO.setOrderTime(ToolUntil.getOrderIdByTime());
        lspOrderInfoRepository.save(lspOrderInfoPO);
        return true;
    }
}
