package com.example.demo.Html.domian.vo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @program: lingshipu
 * @description: 用户信息
 * @author: QWS
 * @create: 2020-05-12 03:56
 */

@Data
public class LspVxUserVO {
    String nickname;
    String  headimgurl;
    String access_token;
}
