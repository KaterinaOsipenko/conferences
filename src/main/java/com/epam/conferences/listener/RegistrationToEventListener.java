package com.epam.conferences.listener;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.EmailService;

public class RegistrationToEventListener implements NotificationListener {

    private final EmailService emailService;

    public RegistrationToEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(User user, Event event) throws ServiceException {
        emailService.send(user.getEmail());
    }
}
