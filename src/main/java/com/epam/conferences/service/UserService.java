package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

public interface UserService {

    boolean findUserById(long userId) throws ServiceException;

    void saveUser(User user) throws ServiceException;

    User findUserByEmail(String email) throws ServiceException;

    void registerUserToEvent(Event event, User user) throws ServiceException;

    void updateUser(User user) throws ServiceException;
}
