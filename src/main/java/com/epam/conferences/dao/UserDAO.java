package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    void insertUser(Connection connection, User user) throws DBException;

    Optional<User> findUser(Connection connection, long userId) throws DBException;

    Optional<User> findUserByEmail(Connection connection, String email) throws DBException;

    Optional<User> updateUser(Connection connection, User user);

    Optional<User> deleteUser(Connection connection, User user);

    List<User> findAllUsers(Connection connection) throws DBException;

}
