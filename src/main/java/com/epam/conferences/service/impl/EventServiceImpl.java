package com.epam.conferences.service.impl;

import com.epam.conferences.dao.DAOFactory;
import com.epam.conferences.dao.EventDAO;
import com.epam.conferences.dao.UserEventDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Address;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.EventService;
import com.epam.conferences.service.NotificationManager;
import com.epam.conferences.service.SortService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService, SortService {

    private static final Logger logger = LogManager.getLogger(EventServiceImpl.class);
    private final EventDAO eventDAO;
    private final UserEventDAO userEventDAO;
    private final NotificationManager notificationManager;

    public EventServiceImpl(EventDAO eventDao, UserEventDAO userEventDAO, NotificationManager notificationManager) {
        if (eventDao == null) {
            logger.error("EventServiceImpl: eventDAO reference in constructor is NULL.");
            throw new IllegalArgumentException("EventServiceImpl: eventDAO reference in constructor is NULL.");
        } else if (userEventDAO == null) {
            logger.error("EventServiceImpl: userEventDAO reference in constructor is NULL.");
            throw new IllegalArgumentException("EventServiceImpl: userEventDAO reference in constructor is NULL.");
        } else if (notificationManager == null) {
            logger.error("EventServiceImpl: notificationManager reference in constructor is NULL.");
            throw new IllegalArgumentException("EventServiceImpl: notificationManager reference in constructor is NULL.");
        }
        this.eventDAO = eventDao;
        this.userEventDAO = userEventDAO;
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
    public int createEvent(Event event) throws ServiceException {
        logger.info("EventServiceImpl: creating event.");
        int id;
        try {
            id = eventDAO.saveEvent(DAOFactory.getConnection(), event);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during creating event.");
            throw new ServiceException(e);
        }
        logger.info("EventServiceImpl: event was created successfully.");
        return id;
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
    public boolean isPastEvent(int id) throws ServiceException {
        Event event = findEvent(id);
        return event.getDate().isBefore(LocalDateTime.now());
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

    @Override
    public void updateEvent(int id, Event event) throws ServiceException {
        logger.info("EventServiceImpl: updating event with id={}.", id);
        try {
            eventDAO.updateEvent(DAOFactory.getConnection(), id, event);
            Event updatedEvent = findEvent(id);
            notifyAllUsers(id, updatedEvent);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception {} during updating event with id={}.", e.getMessage(), id);
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer countEventsByCategory(int id) throws ServiceException {
        logger.info("EventServiceImpl: counting rows by category.");
        Integer count;
        try {
            count = eventDAO.countEventsByCategory(DAOFactory.getConnection(), id);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception during counting all events.");
            throw new ServiceException(e);
        }
        return count;
    }

    @Override
    public List<Event> findEventsByCategory(int page, int pageSize, int id) throws ServiceException {
        logger.info("EventServiceImpl: getting all events for category {}.", id);
        List<Event> eventList;
        try {
            int count = getCountForPage(page, pageSize);
            eventList = eventDAO.findAllEventByCategory(DAOFactory.getConnection(), (page * pageSize - pageSize), count, id);
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
    public Integer maxPage(int pageSize, int id) throws ServiceException {
        logger.info("EventServiceImpl: get max page.");
        int countEvents = countEventsByCategory(id);
        int maxPage = countEvents / pageSize + countEvents % pageSize;
        if (maxPage == 0) {
            maxPage = 1;
        }
        return maxPage;
    }

    @Override
    public void changeAddressEvent(int addressId, int eventId, Address address) throws ServiceException {
        logger.info("EventServiceImpl: changing address with id={}.", addressId);
        try {
            eventDAO.updateAddress(DAOFactory.getConnection(), addressId, address);
            Event event = findEvent(eventId);
            notifyAllUsers(eventId, event);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("EventServiceImpl: exception {} during updating address.", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("EventServiceImpl: address with id={} was updated successfully..", addressId);
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
            eventList = eventDAO.sortByCountReports(DAOFactory.getConnection(), (page * pageSize - pageSize), count);
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

    private void notifyAllUsers(int id, Event event) throws ServiceException, SQLException, NamingException, DBException {
        List<User> users = userEventDAO.findUserRegisteredToEvent(DAOFactory.getConnection(), id);
        for (User user : users) {
            notificationManager.notify("updateEvent", user, event);
        }
    }

}
