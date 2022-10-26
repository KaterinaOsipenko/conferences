package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

import java.util.List;

public interface EventService {

    List<Event> findAll() throws ServiceException;

    List<Event> findEvents(int page) throws ServiceException;

    Integer countEvents() throws ServiceException;

    Integer maxPage() throws ServiceException;

    Event findEvent(int id) throws ServiceException;

    void registerUserToEvent(Event event, User user) throws ServiceException;

    boolean isUserRegisteredToEvent(Event event, User user) throws ServiceException;

    List<Event> findEventsSorted(int page, String sort) throws ServiceException;
}
