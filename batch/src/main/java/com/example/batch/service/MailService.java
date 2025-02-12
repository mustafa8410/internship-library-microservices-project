package com.example.batch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/*
import org.springframework.mail.javamail.JavaMailSenderImpl;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
*/

@Service
public class MailService {

    @Value("${spring.mail.username}")
    String sourceMail;

    /*@Value("${spring.mail.password}")
    String password;

    @Value("${spring.mail.host}")
    String host;

    @Value("${spring.mail.port}")
    int port;*/

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    /*@PostConstruct
    public void configureMailSender(){
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(sourceMail);
        mailSender.setPassword(password);

        Properties mailProps = mailSender.getJavaMailProperties();
        mailProps.setProperty("mail.smtp.auth", "true");
        mailProps.setProperty("mail.smtp.starttls.enable", "true");
        //mailProps.setProperty("mail.smtp.starttls.required", "true");
    }*/

    public void sendMail(String receiver, String title, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sourceMail);
        mailMessage.setTo(receiver);
        mailMessage.setSubject(title);
        mailMessage.setText(message);
        mailSender.send(mailMessage);

    }


    /*public void sendMail(String receiver, String title, String message) throws Exception{
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.smtp.host", host);
        mailProps.setProperty("mail.smtp.user", sourceMail);
        mailProps.setProperty("mail.smtp.port", "25");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sourceMail, password);
            }
        };

        Message mimeMessage = new MimeMessage(Session.getInstance(mailProps, authenticator));
        mimeMessage.setSubject(title);
        mimeMessage.setText(message);
        mimeMessage.setFrom(new InternetAddress(sourceMail, "library"));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

        Transport.send(mimeMessage);


    }*/

}
