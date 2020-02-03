package com.example.demo.controller.old;/*
package com.example.demo.controller;
import com.example.demo.Logic.login.LVx_User;
import com.example.demo.entity.login.Vx_User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class old {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping("getUser")
    public Map<String, Object> getUser() {
        System.out.println("微信小程序正在调用。。。");
        Map<String, Object> children = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("1111111");
        list.add("22222");
        list.add("3333333r");
        list.add("4444444");
        children.put("list", list);
        System.out.println("微信小程序调用完成。。。");
        return children;
    }

    @RequestMapping("getWord")
    public Map<String, Object> getText(String word) {
        Map<String, Object> children = new HashMap<String, Object>();
        String message = "我能力有限，不要为难我";
        if ("后来".equals(word)) {
            message = "正在热映的后来的我们是刘若英的处女作。";
        } else if ("微信小程序".equals(word)) {
            message = "想获取更多微信小程序相关知识，请更多的阅读微信官方文档，还有其他更多微信开发相关的内容，学无止境。";
        } else if ("西安工业大学".equals(word)) {
            message = "西安工业大学（Xi'an Technological University）简称”西安工大“，位于世界历史名城古都西安，是中国西北地区唯一一所以兵工为特色，以工为主，理、文、经、管、法协调发展的教学研究型大学。原中华人民共和国兵器工业部直属的七所本科院校之一（“兵工七子”），陕西省重点建设的高水平教学研究型大学、陕西省人民政府与中国兵器工业集团、国防科技工业局共建高校、教育部“卓越工程师教育培养计划”试点高校、陕西省大学生创新能力培养综合改革试点学校。国家二级保密资格单位，是一所以\"军民结合，寓军于民\"的国防科研高校。";
        }
        children.put("message", message);
        return children;
    }

    @RequestMapping("test")
    public Map<String, Object> queryStudent() {
        Map<String, Object> children = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("student");
        list.add("22222");
        list.add("3333333r");
        list.add("4444444");
        list.add("new 1.25");
        children.put("list", list);
        System.out.println("微信小程序调用完成。。。");
        return children;
    }

    @RequestMapping("jabc")
    public Map<String, Object> queryStudent(Model model, HttpServletRequest request) {

        Map<String, Object> children = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();

        List<Vx_User> vxusers = null;
        LVx_User vxuser = new LVx_User();
        String sqlTxt = "select * from vx_user";
        try {
            vxusers = vxuser.queryCourse(sqlTxt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String put = objectToJson(vxusers);
        model.addAttribute("vxusers", vxusers);
        for (int x = 0; x < 1; x++) {
            list.add(put);
        }
        children.put("list", list);
        return children;
    }


    private static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //非list格式传回json------
    @RequestMapping("test2")
    public Map<String, String> test2(Model model, HttpServletRequest request) {
        Map<String, String> children = new HashMap<String, String>();
        children.put("id", "123");
        children.put("name", "女");
        children.put("pic_url", "http://img09.yiguoimg.com/e/ad/2016/161011/585749449909281099_260x320.jpg");
        children.put("price", "女");
        children.put("type", "女");

        return children;
    }
    //非list格式传回json------

    @RequestMapping("test3")
    public  Map<String, Object>est3(){
        Map<String, Object> children;
        search search = new search();
        children = search.search("select * from vx_homepage_more","vx_homepage_more");
        return children;
    }

    @RequestMapping("huoqu")
    public void huoqu(){
        System.out.println("asdddddddddddddddddddddddddddddddddddddddddd");
    }
}
*/
