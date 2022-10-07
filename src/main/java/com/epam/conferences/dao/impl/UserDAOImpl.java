package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.UserDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);
    private static final String FIND_ALL_USERS = "SELECT * FROM users";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_USER_BY_ID = "UPDATE users SET firstName = ? WHERE id = ?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO users (firstName, lastName, email, password, id_role)" + " VALUES (?, ?, ?, ?, ?)";

    @Override
    public void insertUser(Connection connection, User user) throws DBException {
        logger.info("UserDAOImpl: inserting user {}", user);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            insertParameters(preparedStatement, user);
            preparedStatement.executeUpdate();
            logger.info("UserDAOImpl: {} was insert successfully", user);
        } catch (SQLException e) {
            logger.error("UserDAOImpl: insert user exception: ", e);
            throw new DBException(e);
        }
    }

    @Override
    public Optional<User> findUser(Connection connection, long userId) throws DBException {
        logger.info("UserDAOImpl: search for  user by id {}", userId);
        Optional<User> user;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID)) {
            preparedStatement.setInt(1, (int) userId);
            ResultSet userSet = preparedStatement.executeQuery();
            user = Optional.of(extractUser(userSet));
            logger.info("UserDAOImpl: user was found.");
        } catch (SQLException e) {
            logger.error("UserDAOImpl: find user exception: ", e);
            throw new DBException(e);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(Connection connection, String email) throws DBException {
        logger.info("UserDAOImpl: search for  user by email {}", email);
        Optional<User> user = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet userSet = preparedStatement.executeQuery();
            if (userSet.next()) {
                user = Optional.of(extractUser(userSet));
            }
            user.ifPresent(us -> logger.info("UserDAOImpl: obtain user with email - {}", email));
        } catch (SQLException e) {
            logger.error("UserDAOImpl: find user by email exception: ", e);
            throw new DBException(e);
        }
        return user;
    }

    @Override
    public Optional<User> updateUser(Connection connection, User user) {
        return null;
    }

    @Override
    public Optional<User> deleteUser(Connection connection, User user) {
        return null;
    }

    @Override
    public List<User> findAllUsers(Connection connection) throws DBException {
        logger.info("UserDAOImpl: get list of all users");
        List<User> listUsers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(FIND_ALL_USERS);
            while (set.next()) {
                listUsers.add(extractUser(set));
            }
            logger.info("UserDAOImpl: all users were found.");
        } catch (SQLException e) {
            logger.error("UserDAOImpl: find all users exception.");
            throw new DBException(e);
        }
        return listUsers;
    }

    private void insertParameters(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, String.valueOf(user.getPassword()));
        preparedStatement.setInt(5, user.getRoleId());
    }

    private User extractUser(ResultSet userSet) throws SQLException {
        User user = new User();
        user.setId(userSet.getLong("id"));
        user.setFirstName(userSet.getString("firstName"));
        user.setLastName(userSet.getString("lastName"));
        user.setEmail(userSet.getString("email"));
        user.setPassword(userSet.getString("password").toCharArray());
        user.setRoleId(userSet.getInt("id_role"));
        return user;
    }
}
