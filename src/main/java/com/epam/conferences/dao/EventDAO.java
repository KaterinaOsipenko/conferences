package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Address;
import com.epam.conferences.model.Event;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface EventDAO {

    Integer countReportsByEventId(Connection connection, long eventId) throws DBException;

    Integer countRegisteredUsersByEventId(Connection connection, long eventId) throws DBException;

    List<Event> findAll(Connection connection, int offset, int count) throws DBException;

    List<Event> sortByDate(Connection connection, int offset, int count) throws DBException;

    List<Event> findAllFutureEvents(Connection connection, int offset, int count) throws DBException;

    List<Event> findAllPastEvents(Connection connection, int offset, int count) throws DBException;

    List<Event> sortByCountUsers(Connection connection, int offset, int count) throws DBException;

    List<Event> sortByCountReports(Connection connection, int offset, int count) throws DBException;

    Integer countEvents(Connection connection) throws DBException;

    Integer countEventsByCategory(Connection connection, int id) throws DBException;

    List<Event> findAllEventByCategory(Connection connection, int offset, int count, int id) throws DBException;

    Optional<Event> findById(Connection connection, int id) throws DBException;

    int saveEvent(Connection connection, Event event) throws DBException;

    void updateEvent(Connection connection, int id, Event event) throws DBException;

    void updateAddress(Connection connection, int id, Address address) throws DBException;


}
