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
import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService, SortService {

    private static final Logger logger = LogManager.getLogger(EventServiceImpl.class);
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
    public List<Event> findEvents(int page, int pageSize) throws ServiceException {
        logger.info("EventServiceImpl: getting all events for {}.", page);
        List<Event> eventList;
        try {
            int count = getCountForPage(page, pageSize);
            eventList = eventDAO.findAll(DAOFactory.getConnection(), (page * pageSize - pageSize), count);
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

    public Integer maxPage(int pageSize) throws ServiceException {
        logger.info("EventServiceImpl: get max page.");
        int countEvents = countEvents();
        int maxPage = countEvents / pageSize + countEvents % pageSize;
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
            if (event.isEmpty()) {
                logger.error("EventServiceImpl: there is no event with this id = {}.", id);
                throw new ServiceException("EventServiceImpl: there is no this event.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during obtaining event with id = {}.", id);
            throw new ServiceException(e);
        }
        return event.get();
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
    public List<Event> findEventsSorted(int page, int pageSize, String sort) throws ServiceException {
        logger.info("EventServiceImpl: find needed sort.");
        List<Event> eventList;
        switch (sort) {
            case "date":
                eventList = sortByDate(page, pageSize);
                break;
            case "future":
                eventList = findFutureEvents(page, pageSize);
                break;
            case "past":
                eventList = findPastEvents(page, pageSize);
                break;
            case "reports":
                eventList = sortByReports(page, pageSize);
                break;
            case "users":
                eventList = sortByUsers(page, pageSize);
                break;
            default:
                logger.error("EventServiceImpl: this sort {} is not supported.", sort);
                throw new ServiceException("EventServiceImpl: this sort is not supported.");
        }
        return eventList;
    }

    public List<Event> sortByUsers(int page, int pageSize) throws ServiceException {
        logger.info("EventServiceImpl: sort by regUsers on page {}.", page);
        List<Event> eventList;
        try {
            int count = getCountForPage(page, pageSize);
            eventList = eventDAO.sortByCountUsers(DAOFactory.getConnection(), (page * pageSize - pageSize), count);
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

    public List<Event> sortByReports(int page, int pageSize) throws ServiceException {
        logger.info("EventServiceImpl: sort by reports on page {}.", page);
        List<Event> eventList;
        try {
            int count = getCountForPage(page, pageSize);
            eventList = eventDAO.sortByCountReports(DAOFactory.getConnection(), (page * pageSize - page), count);
            if (eventList == null) {
                logger.error("EventServiceImpl: list of events is NULL.");
                throw new ServiceException("EventServiceImpl: list of events is NULL.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during getting all events.");
            throw new ServiceException(e);
        }
        logger.info("EventServiceImpl: all events sorted by reports for page {} were obtained successfully.", page);
        return eventList;
    }

    public List<Event> findPastEvents(int page, int pageSize) throws ServiceException {
        logger.info("EventServiceImpl: find all in the past on page {}.", page);
        List<Event> eventList;
        try {
            int count = getCountForPage(page, pageSize);
            eventList = eventDAO.findAllPastEvents(DAOFactory.getConnection(), (page * pageSize - pageSize), count);
            if (eventList == null) {
                logger.error("EventServiceImpl: list of events is NULL.");
                throw new ServiceException("EventServiceImpl: list of events is NULL.");
            }
        } catch (DBException | NamingException | SQLException | ServiceException e) {
            logger.error("EventServiceImpl: exception during getting all events in the past.");
            throw new ServiceException(e);
        }
        logger.info("EventServiceImpl: all events in past for page {} were obtained successfully.", page);
        return eventList;
    }

    public List<Event> findFutureEvents(int page, int pageSize) throws ServiceException {
        logger.info("EventServiceImpl: find all in the future on page {}.", page);
        List<Event> eventList;
        try {
            int count = getCountForPage(page, pageSize);
            eventList = eventDAO.findAllFutureEvents(DAOFactory.getConnection(), (page * pageSize - pageSize), count);
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

    public List<Event> sortByDate(int page, int pageSize) throws ServiceException {
        logger.info("EventServiceImpl: sort by date on page {}.", page);
        List<Event> eventList;
        try {
            int count = getCountForPage(page, pageSize);
            eventList = eventDAO.sortByDate(DAOFactory.getConnection(), (page * page - page), count);
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

    private int getCountForPage(int page, int pageSize) throws ServiceException {
        int countEvents = countEvents();
        return countEvents % pageSize != 0 && page == maxPage(pageSize) ? page * pageSize - countEvents == 1 ? 2 : 1 : pageSize;
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
