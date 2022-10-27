package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.Report;

import java.util.List;

public interface ReportService {

    List<Report> getReportsByEventId(Event event) throws ServiceException;
}
