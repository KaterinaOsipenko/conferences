package com.epam.conferences.dao;

import com.epam.conferences.model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class UserDaoImpl implements UserDao {

  private static final String FIND_ALL_USERS = "SELECT * FROM users";

  @Override
  public void insertUser(Connection connection, User user) {

  }
  @Override
  public User findUser(Connection connection, long userId) {
    return null;
  }

  @Override
  public User updateUser(User user) {
    return null;
  }

  @Override
  public User deleteUser(User user) {
    return null;
  }

  @Override
  public List<User> findAllUsers() {
    List<User> listUsers = new ArrayList<>();

    try (Connection connection = DAOFactory.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet set = statement.executeQuery(FIND_ALL_USERS);
      while (set.next()) {
        listUsers.add(extractUser(set));
      }
    } catch (SQLException | NamingException e) {
      throw new RuntimeException(e);
    }

    return listUsers;
  }
  private User extractUser(ResultSet set) throws SQLException {
    User user = new User();
    user.setId(set.getInt("id"));
    return user;
  }
}
