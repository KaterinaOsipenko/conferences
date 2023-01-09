package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

import java.sql.Connection;
import java.util.Optional;

public interface UserDAO {

    void insertUser(Connection connection, User user) throws DBException;

    void insertUserToEvent(Connection connection, Event event, User user) throws DBException;

    Optional<User> findUser(Connection connection, long userId) throws DBException;

    Optional<User> findUserByEmail(Connection connection, String email) throws DBException;

    void updateUser(Connection connection, User user) throws DBException;

}
