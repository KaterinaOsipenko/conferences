package com.epam.conferences.service.impl;

import com.epam.conferences.dao.DAOFactory;
import com.epam.conferences.dao.UserDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.NotificationManager;
import com.epam.conferences.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserDAO userDao;

    private final NotificationManager notificationManager;

    public UserServiceImpl(UserDAO userDao, NotificationManager notificationManager) {
        if (userDao == null) {
            logger.error("UserServiceImpl: UserDao reference in constructor is NULL.");
            throw new IllegalArgumentException("UserServiceImpl: UserDao reference in constructor is NULL.");
        } else if (notificationManager == null) {
            logger.error("UserServiceImpl: NotificationManager reference in constructor is NULL.");
            throw new IllegalArgumentException("UserServiceImpl: NotificationManager reference in constructor is NULL.");
        }
        this.userDao = userDao;
        this.notificationManager = notificationManager;
    }

    @Override
    public boolean findUserById(long userId) throws ServiceException {
        return false;
    }

    @Override
    public void saveUser(User user) throws ServiceException {
        logger.info("UserServiceImpl: saving user.");
        try {
            this.userDao.insertUser(DAOFactory.getConnection(), user);
            notificationManager.notify("registration", user, null);
        } catch (SQLException | NamingException | DBException e) {
            logger.error("UserServiceImpl: exception {} during saving user", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("UserServiceImpl: save user successfully.");
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        logger.info("UserServiceImpl: update user {}.", user);
        try {
            userDao.updateUser(DAOFactory.getConnection(), user);
        } catch (SQLException | NamingException | DBException e) {
            logger.error("UserServiceImpl: exception {} during updating user {}", e.getMessage(), user);
            throw new ServiceException(e);
        }
        logger.info("UserServiceImpl: updated user successfully.");
    }

    @Override
    public User findUserByEmail(String email) throws ServiceException {
        logger.info("UserServiceImpl: check if user exist.");
        Optional<User> userByEmail;
        try {
            userByEmail = userDao.findUserByEmail(DAOFactory.getConnection(), email);
        } catch (SQLException | NamingException | DBException e) {
            logger.error("UserServiceImpl: exception {} during checking existing of user", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("UserServiceImpl: obtain user successfully.");
        return userByEmail.orElse(null);
    }

    @Override
    public void registerUserToEvent(Event event, User user) throws ServiceException {
        logger.info("UserServiceImpl: register user {} to event {}.", user, event);
        try {
            this.userDao.insertUserToEvent(DAOFactory.getConnection(), event, user);
            notificationManager.notify("registrationToEvent", user, event);
        } catch (SQLException | NamingException | DBException e) {
            logger.error("UserServiceImpl: exception {} during registration user {} to event{}", e.getMessage(), user, event);
            throw new ServiceException(e);
        }
        logger.info("UserServiceImpl: registration user to event successfully.");
    }


}
