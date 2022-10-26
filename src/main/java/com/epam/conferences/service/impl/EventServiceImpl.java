package com.epam.conferences.service.impl;

import com.epam.conferences.dao.DAOFactory;
import com.epam.conferences.dao.EventDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.EventService;
import com.epam.conferences.service.NotificationManager;
import com.epam.conferences.service.SortService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService, SortService {

    private static final Logger logger = LogManager.getLogger(EventServiceImpl.class);
    private static final int PAGE_SIZE = 3;
    private final EventDAO eventDAO;

    private final NotificationManager notificationManager;

    public EventServiceImpl(EventDAO eventDao, NotificationManager notificationManager) {
        if (eventDao == null) {
            logger.error("EventServiceImpl: eventDAO reference in constructor is NULL.");
            throw new IllegalArgumentException("EventServiceImpl: eventDAO reference in constructor is NULL.");
        } else if (notificationManager == null) {
            logger.error("EventServiceImpl: notificationManager reference in constructor is NULL.");
            throw new IllegalArgumentException("EventServiceImpl: notificationManager reference in constructor is NULL.");
        }
        this.eventDAO = eventDao;
        this.notificationManager = notificationManager;
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
            int count = getCountForPage(page);
            eventList = eventDAO.findAll(DAOFactory.getConnection(), (page * PAGE_SIZE - PAGE_SIZE), count);
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

    @Override
    public boolean isUserRegisteredToEvent(Event event, User user) throws ServiceException {
        logger.info("EventServiceImpl: checking if user {} has already registered to event {}", user, event);
        boolean result;
        try {
            result = eventDAO.isUserRegisteredToEvent(DAOFactory.getConnection(), event, user);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception ({}) during checking if user {} has already registered to event {}", e.getMessage(), user, event);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Event> findEventsSorted(int page, String sort) throws ServiceException {
        logger.info("EventServiceImpl: find needed sort.");
        List<Event> eventList;
        switch (sort) {
            case "date":
                eventList = sortByDate(page);
                break;
            case "future":
                eventList = findFutureEvents(page);
                break;
            case "past":
                eventList = findPastEvents(page);
                break;
            case "reports":
                eventList = sortByReports(page);
                break;
            case "users":
                eventList = sortByUsers(page);
                break;
            default:
                logger.error("EventServiceImpl: this sort {} is not supported.", sort);
                throw new ServiceException("EventServiceImpl: this sort is not supported.");
        }
        return eventList;
    }

    public List<Event> sortByUsers(int page) {
        logger.info("EventServiceImpl: sort by users on page {}.", page);
        List<Event> eventList = new ArrayList<>();
        return eventList;
    }

    public List<Event> sortByReports(int page) {
        logger.info("EventServiceImpl: sort by reports on page {}.", page);
        List<Event> eventList = new ArrayList<>();
        return eventList;
    }

    public List<Event> findPastEvents(int page) {
        logger.info("EventServiceImpl: find all events in the past on page {}.", page);
        List<Event> eventList = new ArrayList<>();
        return eventList;
    }

    public List<Event> findFutureEvents(int page) throws ServiceException {
        logger.info("EventServiceImpl: find all in the future on page {}.", page);
        List<Event> eventList;
        try {
            int count = getCountForPage(page);
            eventList = eventDAO.findAllFutureEvents(DAOFactory.getConnection(), (page * PAGE_SIZE - PAGE_SIZE), count);
            if (eventList == null) {
                logger.error("EventServiceImpl: list of events is NULL.");
                throw new ServiceException("EventServiceImpl: list of events is NULL.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during getting all events in the future.");
            throw new ServiceException(e);
        }
        logger.info("EventServiceImpl: all events in future for page {} were obtained successfully.", page);
        return eventList;
    }

    public List<Event> sortByDate(int page) throws ServiceException {
        logger.info("EventServiceImpl: sort by date on page {}.", page);
        List<Event> eventList;
        try {
            int count = getCountForPage(page);
            eventList = eventDAO.sortByDate(DAOFactory.getConnection(), (page * PAGE_SIZE - PAGE_SIZE), count);
            if (eventList == null) {
                logger.error("EventServiceImpl: list of events is NULL.");
                throw new ServiceException("EventServiceImpl: list of events is NULL.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during getting all events.");
            throw new ServiceException(e);
        }
        logger.info("EventServiceImpl: all events sorted by date for page {} were obtained successfully.", page);
        return eventList;
    }

    private int getCountForPage(int page) throws ServiceException {
        int countEvents = countEvents();
        return countEvents % PAGE_SIZE != 0 && page == maxPage() ? page * PAGE_SIZE - countEvents == 1 ? 2 : 1 : PAGE_SIZE;
    }

    @Override
    public void registerUserToEvent(Event event, User user) throws ServiceException {
        logger.info("EventServiceImpl: register user {} to {}.", user, event);
        try {
            eventDAO.insertUserToPresence(DAOFactory.getConnection(), event, user);
            notificationManager.notify("registrationToEvent", user, event);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during registration user {} to event {}.", user, event);
            throw new ServiceException(e);
        }
    }
}
