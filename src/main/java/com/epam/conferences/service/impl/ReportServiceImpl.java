package com.epam.conferences.service.impl;

import com.epam.conferences.dao.DAOFactory;
import com.epam.conferences.dao.ReportDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Report;
import com.epam.conferences.service.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LogManager.getLogger(ReportServiceImpl.class);

    private final ReportDAO reportDAO;

    public ReportServiceImpl(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    public List<Report> getReportsByEventId(int eventId) throws ServiceException {
        logger.info("ReportServiceImpl: getting all events of conference with id {}.", eventId);
        List<Report> reports;
        try {
            reports = reportDAO.findAllByEventId(DAOFactory.getConnection(), eventId);
            if (reports == null) {
                logger.error("ReportServiceImpl: list of reports is NULL.");
                throw new ServiceException("ReportServiceImpl: list of reports is NULL.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("ReportServiceImpl: exception ({}) during getting all reports of conference with id {}.", e, eventId);
            throw new ServiceException(e);
        }
        logger.info("ReportServiceImpl: all reports of conference with id {} were obtained successfully.", eventId);
        return reports;
    }

    @Override
    public void deleteReport(int reportId) throws ServiceException {
        logger.info("ReportServiceImpl: deleting report with id {}.", reportId);
        try {
            reportDAO.deleteReport(DAOFactory.getConnection(), reportId);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("ReportServiceImpl: exception ({}) during deleting report with id {}.", e, reportId);
            throw new ServiceException(e);
        }
        logger.info("ReportServiceImpl: removing report was successful!");
    }

    @Override
    public void changeReportTopic(int topicId, String name) throws ServiceException {
        logger.info("ReportServiceImpl: setting new name {} to topic with id={} ", name, topicId);
        try {
            reportDAO.changeReportTopic(DAOFactory.getConnection(), topicId, name);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("ReportServiceImpl: exception ({}) during updating name of topic with id={}.", e, topicId);
            throw new ServiceException(e);
        }
        logger.info("ReportServiceImpl: updating name of topic was successful!");
    }

    @Override
    public List<Report> findReportsByUser(int userId) throws ServiceException {
        logger.info("ReportServiceImpl: getting all reports by user with id {}.", userId);
        List<Report> reports;
        try {
            reports = reportDAO.getReportsByUser(DAOFactory.getConnection(), userId);
            if (reports == null) {
                logger.error("ReportServiceImpl: list of reports is NULL.");
                throw new ServiceException("ReportServiceImpl: list of reports is NULL.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("ReportServiceImpl: exception ({}) during getting all reports by user with id {}.", e, userId);
            throw new ServiceException(e);
        }
        logger.info("ReportServiceImpl: all reports by userwith id {} were obtained successfully.", userId);
        return reports;
    }
}
