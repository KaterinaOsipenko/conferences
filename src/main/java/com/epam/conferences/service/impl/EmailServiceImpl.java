package com.epam.conferences.service.impl;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.service.EmailService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
    private final String port;
    private final String host;
    private final Properties properties;
    private final String USERNAME = "kate.osipenko30@gmail.com";
    private final String PASSWORD = "K30052003o";
    private final String FROM_ADDRESS = "kate.osipenko30@gmail.com";

    public EmailServiceImpl(String port, String host) {
        properties = new Properties();
        this.port = port;
        this.host = host;
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", host);
    }

    public void send(String to) throws ServiceException {
        Email email = new SimpleEmail();
        try {
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("username", "password"));
            email.setFrom("kate.osipenko30@gmail.com");
            email.setSSLOnConnect(true);
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo(to);
            email.send();
        } catch (EmailException exception) {
            logger.error(exception.getCause());
        }


    }
}
