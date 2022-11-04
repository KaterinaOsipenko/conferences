package com.epam.conferences.listener;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.format.DateTimeFormatter;

public class UpdateEventListener implements NotificationListener {

    private static final Logger logger = LogManager.getLogger(UpdateEventListener.class);

    private final EmailService emailService;

    private final String SUBJECT = "Update Event";

    public UpdateEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(User user, Event event) throws ServiceException {
        logger.info("UpdateEventListener: notify user about updating event.");
        String content = "<!DOCTYPE html><html><body>" +
                "<div style=\"background: #35b082; padding: 30px 20px; border-radius: 15px;\">" +
                "<h1 style=\"text-align: center\">UPDATE EVENT</h1>" +
                "<h5>Event was updated!</h5>" +
                "<p>" + user.getEmail() + ", you was registered to " + event.getName() + "!</p>" +
                "<p>We will be waiting for you on " + event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " by the address " + event.getAddress() + "!</p>" +
                "</div></body></html>";
        emailService.send(user.getEmail(), SUBJECT, content);
    }
}
