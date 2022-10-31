package com.epam.conferences.listener;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationListener implements NotificationListener {

    private static final Logger logger = LogManager.getLogger(RegistrationListener.class);

    private final EmailService emailService;

    private final String SUBJECT = "Registration was successfully";

    public RegistrationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(User user, Event event) throws ServiceException {
        logger.info("RegistrationListener: notify user {} about registration.", user.getId());
        String content = "<!DOCTYPE html><html><body>" +
                "<div style=\"background: #35b082; padding: 30px 20px; border-radius: 15px;\">" +
                "<h1 style=\"text-align: center\">Registration Successful</h1>" +
                "<h5>Thank you for the joining to our team!</h5>" +
                "<p>" + user.getFirstName() + ", you can visit site by this link</p>" +
                "<p style=\"text-align: center\"><a style=\"color:black;\" href=\"http://localhost:8080/\">Visit</a></p>" +
                "</div></body></html>";
        emailService.send(user.getEmail(), SUBJECT, content);
    }
}
