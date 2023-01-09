package com.epam.conferences.dao;

import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Topic;

import java.sql.Connection;
import java.util.List;

public interface TopicDAO {

    List<Topic> findAll(Connection connection) throws DBException;

    void deleteTopic(Connection connection, long id) throws DBException;
}
