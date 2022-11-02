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
    public Integer countReportsByEventId(int eventId) throws ServiceException {
        logger.info("ReportServiceImpl: getting count of reports of event with id {}.", eventId);
        int count;
        try {
            count = reportDAO.countReportsByEventId(DAOFactory.getConnection(), eventId);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("ReportServiceImpl: exception ({}) during getting count of reports of event with id {}.", e, eventId);
            throw new ServiceException(e);
        }
        logger.info("ReportServiceImpl: getting count of reports of event with id {} was successfully. Count is {}", eventId, count);
        return count;
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
}
