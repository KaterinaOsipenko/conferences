package com.epam.conferences.service.impl;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
    private final Properties properties;
    private final String PASSWORD = System.getenv("PASSWORD_EMAIL");
    private final String FROM_ADDRESS = "kate.osipenko30@gmail.com";

    public EmailServiceImpl(String port, String host) {
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", host);
    }

    public void send(String to, String subject, String content) throws ServiceException {
        logger.info("EmailServiceImpl: sending email to {} about successful registration.", to);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        try {
            Message message = new MimeMessage(session);
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.setFrom(new InternetAddress(FROM_ADDRESS));
            message.setReplyTo(InternetAddress.parse("no-reply@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html; charset=utf-8");
            multipart.addBodyPart(bodyPart);
            message.setContent(multipart);

            message.setSentDate(new Date());
            Transport.send(message);
            logger.info("EmailServiceImpl: email to {} send successfully", to);
        } catch (MessagingException e) {
            logger.error("EmailServiceImpl: exception {}", e.getMessage());
            throw new ServiceException(e);
        }
    }
}
