package com.example.demo.newlsp;

import com.example.demo.html.domian.po.ReplenishmentPO;
import com.example.demo.html.repository.ReplenishmentRepository;
import com.example.demo.html.service.LspVxUserServiceImpl;
import com.example.demo.html.service.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: lingshipu
 * @description: 其他服务类
 * @author: QWS
 * @create: 2021-04-15 21:45
 */
@Service
public class OthersServiceImpl {
    @Autowired
    LspVxUserServiceImpl lspVxUserService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    ReplenishmentRepository replenishmentRepository;
    /**
     * 更改补货订单状态
     *
     * @param replenishmentItemId 补货订单id
     * @param statusCode               状态码（0、1、2……）
     * @return 1
     */
    public boolean updateReplenishmentStatusCode(Integer replenishmentItemId, Integer statusCode) {
        ReplenishmentPO replenishmentPO = replenishmentRepository.findByReplenishmentItemId(replenishmentItemId);
        if(replenishmentPO==null){
            return false;
        }
        replenishmentPO.setReplenishmentStatusCode(statusCode);
        replenishmentRepository.save(replenishmentPO);
        return true;
    }
}
