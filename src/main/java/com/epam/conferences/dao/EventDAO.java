package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface EventDAO {

    List<Event> findAll(Connection connection) throws DBException;

    List<Event> findAll(Connection connection, int offset, int count) throws DBException;

    List<Event> sortByDate(Connection connection, int offset, int count) throws DBException;

    List<Event> findAllFutureEvents(Connection connection, int offset, int count) throws DBException;

    List<Event> findAllPastEvents(Connection connection, int offset, int count) throws DBException;

    Integer countEvents(Connection connection) throws DBException;

    Optional<Event> findById(Connection connection, int id) throws DBException;

    void insertUserToPresence(Connection connection, Event event, User user) throws DBException;

    boolean isUserRegisteredToEvent(Connection connection, Event event, User user) throws DBException;


}
