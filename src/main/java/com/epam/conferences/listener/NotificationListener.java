package com.epam.conferences.listener;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

public interface NotificationListener {

    void notify(User user, Event event) throws ServiceException;
}
