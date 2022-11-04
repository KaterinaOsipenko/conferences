package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserEventDAO {

    List<User> findUserRegisteredToEvent(Connection connection, int eventId) throws DBException;

    void insertUserToPresence(Connection connection, Event event, User user) throws DBException;

    boolean isUserRegisteredToEvent(Connection connection, Event event, User user) throws DBException;
}
