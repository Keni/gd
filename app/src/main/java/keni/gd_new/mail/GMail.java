package keni.gd_new.mail;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Keni on 14.03.2016.
 */
public class GMail
{
    final String emailPort = "587"; // smtp порт
    final String smtpAuth = "true";
    final String starttls = "true";
    final String emailHost = "smtp.gmail.com";
    final String emailLogin = "testgostd@gmail.com";
    final String emailPassword = "testgd123";
    final String toEmailList = "testgostd@gmail.com";
    final String emailSubject = "Бронирование";

    String emailBody;

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public GMail()
    {
    }

    public GMail(String emailBody)
    {
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);
        Log.i("Gmail", "Почтовый сервер установлен");
    }

    public MimeMessage createEmailMessage() throws AddressException, MessagingException, UnsupportedEncodingException
    {
        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(emailLogin, "Android"));
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailList));

        emailMessage.setSubject(emailSubject);
        //emailMessage.setContent(emailBody, "text/html"); // html email
        emailMessage.setText(emailBody); // text email
        Log.i("GMail", "Письмо создано");
        return emailMessage;
    }

    public void sendEmail() throws AddressException, MessagingException
    {
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, emailLogin, emailPassword);
        Log.i("GMail", "allrecipients: " + emailMessage.getAllRecipients());
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        Log.i("GMail", "Письмо отправленно.");
    }


}
