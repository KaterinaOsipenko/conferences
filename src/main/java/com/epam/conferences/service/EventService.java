package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;

import java.util.List;

public interface EventService {

    List<Event> findAll() throws ServiceException;

    List<Event> findEvents(int page) throws ServiceException;

    Integer countEvents() throws ServiceException;

    Integer maxPage() throws ServiceException;

    Event findEvent(int id) throws ServiceException;
}
