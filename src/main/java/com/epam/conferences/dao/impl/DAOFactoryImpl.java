package com.epam.conferences.dao;

public class DAOFactoryImpl extends DAOFactory {

  @Override
  public UserDao getUserDao() {
    return new UserDaoImpl();
  }
}
