package com.epam.conferences.dao;

import com.epam.conferences.dao.impl.DAOFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {
    private static final Logger logger = LogManager.getLogger(DAOFactory.class);
    private static DAOFactory daoFactory;

    protected DAOFactory() {

    }

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactoryImpl();
        }
        return daoFactory;
    }

    public static Connection getConnection() throws NamingException, SQLException {
        logger.info("Tomcat Pool Connection: get connection");
        return getDataSource().getConnection();
    }

    public static DataSource getDataSource() throws NamingException {
        logger.info("Tomcat Pool Connection: get datasource");
        Context initialContext = new InitialContext();
        Context envCtx = (Context) initialContext.lookup("java:comp/env");
        DataSource dataSource = (DataSource) envCtx.lookup("jdbc/conferences");
        return dataSource;
    }

    public abstract UserDAO getUserDao();

    public abstract EventDAO getEventDao();

    public abstract ReportDAO getReportDao();

}
