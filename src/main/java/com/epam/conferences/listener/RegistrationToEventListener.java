package com.epam.conferences.listener;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.format.DateTimeFormatter;

public class RegistrationToEventListener implements NotificationListener {

    private static final Logger logger = LogManager.getLogger(RegistrationToEventListener.class);

    private final EmailService emailService;

    private final String SUBJECT = "Registration to Event";

    public RegistrationToEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(User user, Event event) throws ServiceException {
        logger.info("RegistrationListener: notify user {} about registration.", user.getId());
        String content = "<!DOCTYPE html><html><body>" +
                "<div style=\"background: #35b082; padding: 30px 20px; border-radius: 15px;\">" +
                "<h1 style=\"text-align: center\">Registration to Event Successful</h1>" +
                "<h5>Thank you for the joining to our team!</h5>" +
                "<p>" + user.getEmail() + ", you was registered to " + event.getName() + "!</p>" +
                "<p>We will be waiting for you on " + event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " by the address " + event.getAddress() + "!</p>" +
                "<p style=\"text-align: center\"><a style=\"color:black;\" href=\"http://localhost:8080/\">Visit</a></p>" +
                "</div></body></html>";
        emailService.send(user.getEmail(), SUBJECT, content);
    }
}
