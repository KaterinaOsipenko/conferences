package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;

import java.util.List;

public interface SortService {
    List<Event> sortByDate(int page, int pageSize) throws ServiceException;

    List<Event> sortByReports(int page, int pageSize) throws ServiceException;

    List<Event> sortByUsers(int page, int pageSize) throws ServiceException;

    List<Event> findFutureEvents(int page, int pageSize) throws ServiceException;

    List<Event> findPastEvents(int page, int pageSize) throws ServiceException;
}
