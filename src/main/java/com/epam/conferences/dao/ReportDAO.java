package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Report;

import java.sql.Connection;
import java.util.List;

public interface ReportDAO {

    List<Report> findAllByEventId(Connection connection, long eventId) throws DBException;

    Integer countReportsByEventId(Connection connection, long eventId) throws DBException;

    void deleteReport(Connection connection, int reportId) throws DBException;
}
