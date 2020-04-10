import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class MailMix {
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
                return new PasswordAuthentication("","");
            }
        });
        session.setDebug(true);

        Transport ts = session.getTransport();
        ts.connect("smtp.qq.com","","");

        MimeMessage message = new MimeMessage(session);
        message.setSubject("随便说点什么");
        message.setFrom(new InternetAddress(""));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(""));


        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("mail-teat/src/main/resources/image.jpg"));
        image.setDataHandler(dh);
        image.setContentID("image.jpg");

        MimeBodyPart text = new MimeBodyPart();
        text.setContent("this is a take image<br/><img src='cid:image.jpg'><br/>的邮件","text/html;charset=UTF-8");

        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();

        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
    }
}
