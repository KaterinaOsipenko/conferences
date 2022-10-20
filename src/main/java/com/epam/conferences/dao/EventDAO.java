package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Event;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface EventDAO {

    List<Event> findAll(Connection connection) throws DBException;

    List<Event> findAll(Connection connection, int offset, int count) throws DBException;

    Integer countEvents(Connection connection) throws DBException;

    Optional<Event> findById(Connection connection, int id) throws DBException;


}
