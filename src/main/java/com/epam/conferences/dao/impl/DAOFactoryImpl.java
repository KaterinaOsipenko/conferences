package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.*;
import com.epam.conferences.exception.DBException;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactoryImpl extends DAOFactory {

    @Override
    public UserDAO getUserDao() {
        return new UserDAOImpl();
    }

    @Override
    public EventDAO getEventDao() {
        return new EventDAOImpl();
    }

    @Override
    public ReportDAO getReportDao() {
        return new ReportDAOImpl();
    }

    @Override
    public UserEventDAO getUserEventDao() {
        return new UserEventDAOImpl();
    }

    @Override
    public void rollback(Connection connection) throws DBException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
