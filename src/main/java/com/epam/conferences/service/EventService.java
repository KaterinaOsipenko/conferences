package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Address;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

import java.util.List;

public interface EventService {

    List<Event> findEvents(int page, int pageSize) throws ServiceException;

    Integer countEvents() throws ServiceException;

    Integer maxPage(int pageSize) throws ServiceException;

    Event findEvent(int id) throws ServiceException;

    void registerUserToEvent(Event event, User user) throws ServiceException;

    boolean isUserRegisteredToEvent(Event event, User user) throws ServiceException;

    boolean isPastEvent(int id) throws ServiceException;

    List<Event> findEventsSorted(int page, int pageSize, String sort) throws ServiceException;

    void updateEvent(int id, Event event) throws ServiceException;

    void changeAddressEvent(int addressId, Address address) throws ServiceException;
}
