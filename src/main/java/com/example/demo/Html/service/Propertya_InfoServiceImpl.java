package com.example.demo.Html.service;

import com.example.demo.Html.repository.PropertyaDao;
import com.example.demo.Html.repository.Propertya_InfoDao;
import com.example.demo.Html.domian.Propertya_Info;
import com.example.demo.Logic.Community.LUserGetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author ：Q
 * @date ：Created in 2019/3/24 17:37
 * @description：${description}
 */
@Service
@Transactional
public class Propertya_InfoServiceImpl  {
    @Autowired
    Propertya_InfoDao propertya_infoDao;
    @Autowired
    LUserGetInfo lUserGetInfo;
    @Autowired
    PropertyaDao propertyaDao;

    //发布公告
    public boolean submitPropertyInfo(String property_info_tittle, String property_info,HttpSession httpSession) throws IOException {
        try {
            String user_name = httpSession.getAttribute("user_name").toString();
            Propertya_Info propertya_info = new Propertya_Info();
            propertya_info.setProperty_info_user_touxiang(propertyaDao.getByPropertyaLoginname(user_name).getProperty_info_user_touxiang());
            propertya_info.setPropertyInfoTittle(property_info_tittle);
            propertya_info.setProperty_info(property_info);
            propertya_info.setProperty_info_replyNumber("0");
            propertya_info.setPropertyInfoUser(user_name);
            Timestamp d = new Timestamp(System.currentTimeMillis());
            propertya_info.setProperty_info_time(d);
            propertya_infoDao.save(propertya_info);
            lUserGetInfo.updateAllProperty_info_replyNumber();
            return true;
        }catch (Exception exception){
            return false;
        }
    }
    //删除公告
    public boolean deleteByPropertyInfoTittle(String[] property_info_tittle,String user_name)throws IOException{

            for (String s : property_info_tittle) {
                try {
                propertya_infoDao.deleteByPropertyInfoTittleAndPropertyInfoUser(s,user_name);
                }catch (Exception exception){
                    return false;
                }
            }
            return true;
    }
}
