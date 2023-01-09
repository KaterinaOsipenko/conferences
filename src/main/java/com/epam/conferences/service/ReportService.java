package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Report;

import java.util.List;

public interface ReportService {

    List<Report> getReportsByEventId(int eventId) throws ServiceException;

    void deleteReport(int reportId) throws ServiceException;

    void changeReportTopic(int topicId, String name) throws ServiceException;

    List<Report> findReportsByUser(int userId) throws ServiceException;
}
