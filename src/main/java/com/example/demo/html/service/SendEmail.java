package com.example.demo.html.service;

import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmail {
    public void main(String name, String email, String message) {
        // TODO Auto-generated method stub
        Properties prop = new Properties();
        prop.put("mail.host", "smtp.163.com");
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        try {
            // 使用java发送邮件5步骤
            // 1.创建sesssion
            Session session = Session.getInstance(prop);
            // 开启session的调试模式，可以查看当前邮件发送状态
            session.setDebug(true);

            Transport ts;
            // 2.通过session获取Transport对象（发送邮件的核心API）
            ts = session.getTransport();
            // 3.通过邮件用户名密码链接
            ts.connect("windcosp@163.com", "4WSOirGg18gZji75");
            // 4.创建邮件
            Message msg = createSimpleMail(session, name, email, message);
            // 5.发送电子邮件
            ts.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static MimeMessage createSimpleMail(Session session, String name, String email, String message)
            throws AddressException, MessagingException {
        // 创建邮件对象
        MimeMessage mm = new MimeMessage(session);
        // 设置发件人
        mm.setFrom(new InternetAddress("windcosp@163.com"));
        // 设置收件人
        mm.setRecipient(Message.RecipientType.TO, new InternetAddress(
                "windcosp@163.com"));
        // 设置抄送人
        //mm.setRecipient(Message.RecipientType.CC, new InternetAddress(
        //      "用户名@163.com"));

        mm.setSubject(name);
        mm.setContent("姓名：" + name + "联系方式：" + email + "消息：" + message, "text/html;charset=utf-8");

        return mm;
    }

}
