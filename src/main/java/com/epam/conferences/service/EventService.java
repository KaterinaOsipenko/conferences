package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Address;
import com.epam.conferences.model.Event;

import java.util.List;

public interface EventService {

    List<Event> findEvents(int page, int pageSize) throws ServiceException;

    Integer countEvents() throws ServiceException;

    Integer maxPage(int pageSize) throws ServiceException;

    Event findEvent(int id) throws ServiceException;

    boolean isPastEvent(int id) throws ServiceException;

    List<Event> findEventsSorted(int page, int pageSize, String sort) throws ServiceException;

    void updateEvent(int id, Event event) throws ServiceException;

    void changeAddressEvent(int addressId, int eventId, Address address) throws ServiceException;
}
