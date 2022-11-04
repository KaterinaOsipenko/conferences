package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

public interface UserEventService {

    void registerUserToEvent(Event event, User user) throws ServiceException;

    boolean isUserRegisteredToEvent(Event event, User user) throws ServiceException;
}
