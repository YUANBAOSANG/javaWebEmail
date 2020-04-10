package util;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.SneakyThrows;
import pojo.User;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SendEmail extends Thread {
    private User user;
    private InputStream inputStream;
    public SendEmail(InputStream inputStream,User user){
        this.inputStream = inputStream;
        this.user = user;
    }

    public SendEmail(User user){
        this.user = user;
    }


    @SneakyThrows
    @Override
    public void run()  {
        //创建一个配置文件
        Properties prop = new Properties();
        //定位到qq邮件服务器
        prop.setProperty("mail.host","smtp.qq.com");
        //设置邮件发送协议
        prop.setProperty("mail.transport.protocol","smtp");
        // 需要验证用户名密码
        prop.setProperty("mail.smtp.auth","true");
        //因为QQ有一个SSL加密所以也需要设置一下，如果是其他邮箱就不要这一步
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.socketFactory",sf);
        //创建session保证在整个会话中有效
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("","");
            }
        });
        //开启debug模式，打印运行状态
        session.setDebug(true);
        //得到连接对象，并通过用户账号与授权码连接上服务器
        Transport ts = session.getTransport();
        ts.connect("smtp.qq.com","","");
        //创建邮件

        MimeMessage message = new MimeMessage(session);
        //设置主题
        message.setSubject("你好!"+user.getUserName());
        //设置发送人
        message.setFrom(new InternetAddress(""));
        //TO表示主要接收人，CC表示抄送人，BCC表示秘密抄送人、
        //设置收件人
        message.setRecipient(Message.RecipientType.TO,
                new InternetAddress(user.getEmail()));
        //附件（这里是照片）
        MimeBodyPart image=null;
        //如需要本地图片的方式就用以下代码。而不用ByteArrayDataSource类
        //FileDataSource file = new FileDataSource("E:\\javaWeb\\javaweb-01-maven02\\mail-teat\\
        // target\\mail-teat\\WEB-INF\\update\\image.jpg");
        if(inputStream!=null) {
            //第二个参数为MIME类型，可自行百度
            DataSource source = new ByteArrayDataSource(inputStream, "image/jpeg");
            image = new MimeBodyPart();
            DataHandler dh = new DataHandler(source);
            image.setDataHandler(dh);
            image.setContentID("image.jpg");
        }
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("<h1>恭喜您！<h1> <p style=\"color: pink\">"+user.getUserName()+
                //如果不在此处加以标注为img，就会作为附件为bin的格式发送
                "</p><br/><img src='cid:image.jpg'>" +
                "<br/> 注册成功，您的密码为"
                +user.getPassword()+"请妥善保管","text/html;charset=UTF-8");

        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        if(inputStream!=null) {
            mm.addBodyPart(image);
        }
        //选择封装方式，有三种
        mm.setSubType("related");
        message.setContent(mm);
        message.saveChanges();
        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
        if(inputStream!=null) {
            inputStream.close();
        }
    }
    public void setUser(User user) {
        this.user = user;
    }
}
