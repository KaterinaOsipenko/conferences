package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.EventDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Address;
import com.epam.conferences.model.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventDAOImpl implements EventDAO {

    private static final Logger logger = LogManager.getLogger(EventDAOImpl.class);

    private static final String FIND_ALL_EVENTS = "SELECT * FROM events JOIN addresses a on a.id = events.id_address";
    private static final String FIND_ALL_EVENTS_BY_PAGE = "SELECT * FROM events JOIN addresses on addresses.id = events.id_address " +
            "ORDER BY events.id LIMIT ? OFFSET ?";

    private static final String COUNT_EVENTS = "SELECT COUNT(*) FROM events";

    private static final String FIND_EVENT_BY_ID = "SELECT * FROM events JOIN addresses a on a.id = events.id_address WHERE events.id = ?";

    @Override
    public List<Event> findAll(Connection connection) throws DBException {
        logger.info("EventDAOImpl: find all events.");
        List<Event> eventList;
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(FIND_ALL_EVENTS);
            eventList = extractEventList(resultSet);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: find all events exception.");
            throw new DBException("EventDAOImpl: find all events exception.");
        }
        logger.info("EventDAOImpl: all events were found.");
        return eventList;
    }

    @Override
    public List<Event> findAll(Connection connection, int offset, int count) throws DBException {
        logger.info("EventDAOImpl: find all events by page.");
        List<Event> eventList;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_EVENTS_BY_PAGE)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            eventList = extractEventList(resultSet);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during getting all events by page.");
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were found.");
        return eventList;
    }

    public Integer countEvents(Connection connection) throws DBException {
        logger.info("EventDAOImpl: count events.");
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_EVENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during counting rows.");
            throw new DBException(e);
        }
        logger.info("EventDAOImpl: all events were calculated.");
        return count;
    }

    @Override
    public Optional<Event> findById(Connection connection, int id) throws DBException {
        logger.info("EventDAOImpl: obtaining event by id={}", id);
        Optional<Event> event = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_EVENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                event = Optional.of(extractEvent(resultSet));
            }
            event.ifPresent(e -> logger.info("EventDAOImpl: event with id={} was get successfully.", id));
        } catch (SQLException e) {
            logger.error("EventDAOImpl: exception during obtaining event with id={}.", id);
            throw new DBException(e);
        }
        return event;
    }

    private List<Event> extractEventList(ResultSet resultSet) throws SQLException {
        List<Event> eventList = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Event> event = Optional.ofNullable(extractEvent(resultSet));
            event.ifPresent(eventList::add);
        }
        return eventList;
    }

    private Event extractEvent(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setId(resultSet.getInt("id_address"));
        address.setCountry(resultSet.getString("country"));
        address.setCity(resultSet.getString("city"));
        address.setStreet(resultSet.getString("street"));
        address.setHouse(resultSet.getInt("numberBuilding"));
        address.setApartment(resultSet.getInt("numberApartment"));

        return new Event.EventBuilder()
                .setId(resultSet.getLong("id"))
                .setDate(resultSet.getTimestamp("date").toLocalDateTime())
                .setName(resultSet.getString("name"))
                .setAddress(address)
                .setDescription(resultSet.getString("description"))
                .build();
    }
}
