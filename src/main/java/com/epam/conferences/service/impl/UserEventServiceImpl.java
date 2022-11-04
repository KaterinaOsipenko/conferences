package com.epam.conferences.service.impl;

import com.epam.conferences.dao.DAOFactory;
import com.epam.conferences.dao.UserEventDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.NotificationManager;
import com.epam.conferences.service.UserEventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;

public class UserEventServiceImpl implements UserEventService {

    private static final Logger logger = LogManager.getLogger(UserEventServiceImpl.class);

    private final UserEventDAO userEventDAO;

    private final NotificationManager notificationManager;

    public UserEventServiceImpl(UserEventDAO userEventDAO, NotificationManager notificationManager) {
        if (userEventDAO == null) {
            logger.error("UserEventServiceImpl: eventDAO reference in constructor is NULL.");
            throw new IllegalArgumentException("UserEventServiceImpl: eventDAO reference in constructor is NULL.");
        } else if (notificationManager == null) {
            logger.error("UserEventServiceImpl: notificationManager reference in constructor is NULL.");
            throw new IllegalArgumentException("UserEventServiceImpl: notificationManager reference in constructor is NULL.");
        }
        this.userEventDAO = userEventDAO;
        this.notificationManager = notificationManager;
    }

    @Override
    public void registerUserToEvent(Event event, User user) throws ServiceException {
        logger.info("UserEventServiceImpl: register user {} to {}.", user, event);
        try {
            userEventDAO.insertUserToPresence(DAOFactory.getConnection(), event, user);
            notificationManager.notify("registrationToEvent", user, event);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("UserEventServiceImpl: exception during registration user {} to event {}.", user, event);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserRegisteredToEvent(Event event, User user) throws ServiceException {
        logger.info("UserEventServiceImpl: checking if user {} has already registered to event {}", user, event);
        boolean result;
        try {
            result = userEventDAO.isUserRegisteredToEvent(DAOFactory.getConnection(), event, user);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("UserEventServiceImpl: exception ({}) during checking if user {} has already registered to event {}", e.getMessage(), user, event);
            throw new ServiceException(e);
        }
        return result;
    }
}
