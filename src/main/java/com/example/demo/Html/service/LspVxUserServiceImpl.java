package com.example.demo.Html.service;

import com.example.demo.Html.domian.po.UserInfoModel;
import com.example.demo.Html.domian.po.VxUserModel;
import com.example.demo.Html.domian.vo.LspVxUserVO;
import com.example.demo.Html.repository.UserInfoDao;
import com.example.demo.Html.repository.VxUserRepository;
import com.example.demo.controller.login.HttpRequest;
import com.example.demo.db.DbManage;
import net.bytebuddy.asm.Advice;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: lingshipu
 * @description: 用户信息
 * @author: QWS
 * @create: 2020-05-11 17:29
 */
@Service
public class LspVxUserServiceImpl {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    VxUserRepository vxUserRepository;
    @Autowired
    ShopInfoServiceImpl shopInfoService;
    //小程序唯一标识   (在微信小程序管理后台获取)
    private String wxspAppid = "wxb6ee00c109bb71fe";
    //小程序的 app secret (在微信小程序管理后台获取)
    private String wxspSecret = "131e5242afe1ae555970369559636c7d";
    //授权（必填）
    private String grant_type = "authorization_code";

    /**
     * 保存session
     *
     * @param openid      用户识别码
     * @param session_key 会话控制码
     * @return 1
     */
    public int saveSessionKey(String openid, String session_key, int device_id) {
        if (openid == null) {
            return 0;
        }
        if(shopInfoService.findShopInfoByShopId(device_id)==null){
            return 0;
        }
        String sqlText = "INSERT into vx_user (vx_user_openid,session_key,shop_id) VALUES('" + openid + "','" + session_key + "','" + device_id + "')";
        System.out.println(sqlText);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        return jdbcTemplate.update(sqlText);
    }

    /**
     * 保存用户信息
     *
     * @param openid openid
     * @param place  学校寝室号
     */
    public void saveUserInfo(String openid, int place, String name, String gerder) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setBuyNum(0);
        userInfoModel.setOpenid(openid);
        userInfoModel.setShoppingAmount(0);
        userInfoModel.setShop_id(place);
        userInfoModel.setUserName(name);
        userInfoModel.setUserGerder(gerder);
        userInfoModel.setUserAddress(shopInfoService.findShopInfoByShopId(place).getShopAddress());
        userInfoDao.save(userInfoModel);
    }

    /**
     * 更新session
     *
     * @param openid      用户识别码
     * @param session_key 会话控制码
     * @return 1
     */
    public int updateSessionKey(String openid, String session_key) {
        String sqlText = "UPDATE vx_user set session_key ='" + session_key + "' where vx_user_openid ='" + openid + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        return jdbcTemplate.update(sqlText);
    }

    /**
     * 查询openid记录数
     *
     * @param openid openid
     * @return 条数
     */
    public int CountByOpenid(String openid) {
        String sqlTxt = "select count(*) from vx_user where vx_user_openid = '" + openid + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        int count = 0;
        for (Map<String, Object> map1 : list) {
            count = Integer.parseInt(map1.get("count(*)").toString());
        }
        return count;
    }

    /**
     * 获取refresh_token
     *
     * @param access_token access_token
     * @return 1
     */
    public String getRefreshToken(String access_token) {
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&grant_type=refresh_token" + "&refresh_token=" + access_token;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/oauth2/refresh_token", params);

        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        //用户的唯一标识（openid）
        return (String) json.get("refresh_token");
    }

    /**
     * 获取用户信息
     *
     * @param access_token token
     * @return 头像和昵称
     */
    public LspVxUserVO getUserHeadAndNickName(String access_token, String openid) {
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        //发送请求
        String sr = HttpRequest.sendGet(" https://api.weixin.qq.com/sns/userinfo", params);
        System.out.println(sr);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        //用户的唯一标识（openid）
        LspVxUserVO lspVxUserVO = new LspVxUserVO();
        lspVxUserVO.setNickname(json.get("nickname").toString());
        lspVxUserVO.setHeadimgurl(json.get("headimgurl").toString());
        lspVxUserVO.setAccess_token(access_token);
        return lspVxUserVO;
    }

    /**
     * 获取用户信息
     *
     * @param access_token token
     * @return 昵称和性别
     */
    public String[] getUserNickNameAndGerder(String access_token, String openid) {
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        //发送请求
        String sr = HttpRequest.sendGet(" https://api.weixin.qq.com/sns/userinfo", params);
        System.out.println(sr);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        //用户的唯一标识（openid）
        String[] arrary = new String[2];
        arrary[0] = json.get("nickname").toString();
        arrary[1] = json.get("sex").toString();
        return arrary;
    }

    /**
     * 获取用户openid
     *
     * @param code code
     * @return 1
     */
    public String decodeUserInfo(String code) {
        System.out.println("code = " + code);
        Map map = new HashMap();

        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return "Error";
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wxb6ee00c109bb71fe";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "131e5242afe1ae555970369559636c7d";
        //授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token", params);

        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        //用户的唯一标识（openid）
        String access_token = (String) json.get("access_token");
        String openid = (String) json.get("openid");
        return openid + "|" + access_token;
    }

    /**
     * 获取openid
     *
     * @param token toekn
     * @return 1
     */
    public String getOpenidByToken(String token) {
        return vxUserRepository.findBySessionKey(token).getVxUserOpenid();
    }

    /**
     * 获取用户信息
     *
     * @param token token
     * @return 1
     */
    public VxUserModel getByToken(String token) {
        return vxUserRepository.findBySessionKey(token);
    }

    /**
     * 获取寝室id
     *
     * @param token toekn
     * @return 1
     */
    public int getShopIdByToken(String token) {
        return vxUserRepository.findBySessionKey(token).getShopId();
    }
}
