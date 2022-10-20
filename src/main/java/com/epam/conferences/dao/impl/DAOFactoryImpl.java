package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.DAOFactory;
import com.epam.conferences.dao.EventDAO;
import com.epam.conferences.dao.ReportDAO;
import com.epam.conferences.dao.UserDAO;

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
}
