package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Report;

import java.sql.Connection;
import java.util.List;

public interface ReportDAO {

    List<Report> findAllByEventId(Connection connection, long eventId) throws DBException;

    void deleteReport(Connection connection, int reportId) throws DBException;

    void changeReportTopic(Connection connection, int topicId, String name) throws DBException;

    List<Report> getReportsByUser(Connection connection, int userId) throws DBException;
}
