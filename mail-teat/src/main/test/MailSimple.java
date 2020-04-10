import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

//oytyrcxjfjdxbfhc
public class MailSimple {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.host","smtp.qq.com");
        prop.setProperty("mail.transport.protocol","smtp");
        prop.setProperty("mail.smtp.auth","true");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.socketFactory",sf);

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("920475030@qq.com","oytyrcxjfjdxbfhc");
            }
        });
        session.setDebug(true);

        Transport ts = session.getTransport();
        ts.connect("smtp.qq.com","","");

        MimeMessage message = new MimeMessage(session);
        message.setSubject("随便说点什么");
        message.setFrom(new InternetAddress(""));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(""));
        message.setContent("<h1 style='color:red'>我就随便说下</h1>","text/html;charset=utf-8");

        ts.sendMessage(message,message.getAllRecipients());
        ts.close();

    }
}
