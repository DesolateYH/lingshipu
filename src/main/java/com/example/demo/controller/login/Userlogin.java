package com.example.demo.controller.login;


import com.example.demo.Logic.login.LUserLogin;
import com.example.demo.Logic.login.LVx_User;
import com.example.demo.Logic.login.Openid_Session_key;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Userlogin {
    /**
     * 解密用户敏感数据
     *
     * @param encryptedData 明文,加密数据
     * @param iv            加密算法的初始向量
     * @param code          用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取 session_key api，将 code 换成 openid 和 session_key
     * @return
     */

    //登陆接口
    @ResponseBody
    @PostMapping("/decodeUserInfo")
    public Map decodeUserInfo(String encryptedData, String iv, String code) {
        LUserLogin lUserLogin = new LUserLogin();
        return lUserLogin.decodeUserInfo(encryptedData,iv,code);
    }
}



