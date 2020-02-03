package com.example.demo.Logic.login;

import com.example.demo.controller.login.AesCbcUtil;
import com.example.demo.controller.login.HttpRequest;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LUserLogin {
    public Map decodeUserInfo(String encryptedData, String iv, String code) {
        Map map = new HashMap();

        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wx6756d2f07bb8c2b3";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "6e6e7beb6519c16fa030cbca56b0c398";
        //授权（必填）
        String grant_type = "authorization_code";


        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);

        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");
                JSONObject userInfoJSON = JSONObject.fromObject(result);

                //System.out.println("ooooooooooooooo" + userInfoJSON.get("openId"));
                //System.out.println("nnnnnnnnnnnnnnn" + userInfoJSON.get("nickName"));

                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));

                //将session_key回传前端
                userInfo.put("session_key", session_key);

                map.put("userInfo", userInfo);

                //若数据库中没有现有的openid，则增加新的openid
                //线路1**步骤1**创建对象，调用函数
                LVx_User lvx_user = new LVx_User();
                lvx_user.relay((String) userInfoJSON.get("openId"), (String) userInfoJSON.get("nickName"));

                //将openid和session_key关联起来，若已经存在上一个session_key，则更新session_key
                //线路2**步骤1**创建对象，调用函数
                Openid_Session_key openid_session_key = new Openid_Session_key();
                openid_session_key.correlation_session_key_openid(openid, session_key);

                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }
}
