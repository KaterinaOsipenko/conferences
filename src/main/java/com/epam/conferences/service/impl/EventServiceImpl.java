package com.epam.conferences.service.impl;

import com.epam.conferences.dao.DAOFactory;
import com.epam.conferences.dao.EventDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService {

    private static final Logger logger = LogManager.getLogger(EventServiceImpl.class);
    private static final int PAGE_SIZE = 3;
    private final EventDAO eventDAO;

    public EventServiceImpl(EventDAO eventDao) {
        if (eventDao == null) {
            logger.error("EventServiceImpl: eventDAO reference in constructor is NULL.");
            throw new IllegalArgumentException("EventServiceImpl: eventDAO reference in constructor is NULL.");
        }
        this.eventDAO = eventDao;
    }

    @Override
    public List<Event> findAll() throws ServiceException {
        logger.info("EventServiceImpl: getting all events.");
        List<Event> eventList;
        try {
            eventList = eventDAO.findAll(DAOFactory.getConnection());
            if (eventList == null) {
                logger.error("EventServiceImpl: list of events is NULL.");
                throw new ServiceException("EventServiceImpl: list of events is NULL.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: find all exception. ");
            throw new ServiceException("EventServiceImpl: find all exception. ");
        }
        logger.info("EventServiceImpl: all events were obtained successfully.");
        return eventList;
    }

    @Override
    public List<Event> findEvents(int page) throws ServiceException {
        logger.info("EventServiceImpl: getting all events for {}.", page);
        List<Event> eventList;
        try {
            int countEvents = countEvents();
            if (countEvents % PAGE_SIZE != 0 && page == maxPage()) {
                if (page * PAGE_SIZE - countEvents == 1) {
                    eventList = eventDAO.findAll(DAOFactory.getConnection(), (page * PAGE_SIZE - PAGE_SIZE), 2);
                } else {
                    eventList = eventDAO.findAll(DAOFactory.getConnection(), (page * PAGE_SIZE - PAGE_SIZE), 1);
                }
            } else {
                eventList = eventDAO.findAll(DAOFactory.getConnection(), (page * PAGE_SIZE - PAGE_SIZE), PAGE_SIZE);
            }
            if (eventList == null) {
                logger.error("EventServiceImpl: list of events is NULL.");
                throw new ServiceException("EventServiceImpl: list of events is NULL.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during getting all events.");
            throw new ServiceException(e);
        }
        logger.info("EventServiceImpl: all events for page {} were obtained successfully.", page);
        return eventList;
    }

    @Override
    public Integer countEvents() throws ServiceException {
        logger.info("EventServiceImpl: counting rows.");
        Integer count;
        try {
            count = eventDAO.countEvents(DAOFactory.getConnection());
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during counting all events.");
            throw new ServiceException(e);
        }
        return count;
    }

    public Integer maxPage() throws ServiceException {
        logger.info("EventServiceImpl: get max page.");
        int countEvents = countEvents();
        int maxPage = countEvents / 3 + countEvents % 3;
        if (maxPage == 0) {
            maxPage = 1;
        }
        return maxPage;
    }

    @Override
    public Event findEvent(int id) throws ServiceException {
        logger.info("EventServiceImpl: get event by id = {}.", id);
        Optional<Event> event;
        try {
            event = eventDAO.findById(DAOFactory.getConnection(), id);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during counting all events.");
            throw new ServiceException(e);
        }
        return event.orElse(null);
    }
}
