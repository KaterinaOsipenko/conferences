package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.UserEventDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserEventDAOImpl implements UserEventDAO {

    private static final Logger logger = LogManager.getLogger(UserEventDAOImpl.class);

    private final static String FIND_USERS_BY_ID = "SELECT users.email FROM user_event_presence JOIN users ON users.id = user_event_presence.id_user WHERE id_event = ?";

    private static final String INSERT_USER_TO_PRESENCE = "INSERT INTO user_event_presence (id_user, id_event) VALUES (?, ?)";

    private static final String CHECK_USER_REGISTERED = "SELECT * FROM user_event_presence WHERE id_user = ? AND id_event = ?";

    @Override
    public List<User> findUserRegisteredToEvent(Connection connection, int eventId) throws DBException {
        logger.info("UserEventDAOImpl: find all registered users to event with id={}.", eventId);
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USERS_BY_ID)) {
            preparedStatement.setLong(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = extractUsersList(resultSet);
        } catch (SQLException e) {
            logger.error("UserEventDAOImpl: exception ({}) during obtaining all registered users to event with id={}.", e.getMessage(), eventId);
            throw new DBException(e);
        }
        logger.info("UserEventDAOImpl: fall registered users to event with id={} were get successfully.", eventId);
        return users;
    }

    @Override
    public void insertUserToPresence(Connection connection, Event event, User user) throws DBException {
        logger.info("EventDAOImpl: insert user with id={} and event with id={}", user.getId(), event.getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_TO_PRESENCE)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, event.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception ({}) during insertion user with id={} and event with id={}", e.getMessage(), user.getId(), event.getId());
            throw new DBException(e);
        }
    }

    @Override
    public boolean isUserRegisteredToEvent(Connection connection, Event event, User user) throws DBException {
        logger.info("EventDAOImpl: check if user with id={} has already registered to event with id={}", user.getId(), event.getId());
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USER_REGISTERED)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, event.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception ({}) during insertion user with id={} and event with id={}", e.getMessage(), user.getId(), event.getId());
            throw new DBException(e);
        }
    }

    private List<User> extractUsersList(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            Optional<User> event = Optional.of(extractUser(resultSet));
            event.ifPresent(userList::add);
        }
        return userList;
    }

    private User extractUser(ResultSet userSet) throws SQLException {
        User user = new User();
        user.setEmail(userSet.getString("email"));
        return user;
    }
}
