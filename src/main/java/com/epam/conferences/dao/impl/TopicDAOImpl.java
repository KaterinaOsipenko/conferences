package com.epam.conferences.dao.impl;

import com.epam.conferences.dao.TopicDAO;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.model.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TopicDAOImpl implements TopicDAO {

    private static final Logger logger = LogManager.getLogger(TopicDAOImpl.class);

    private static final String FIND_ALL_TOPICS = "SELECT * FROM topics";

    private static final String DELETE_TOPIC_BY_ID = "DELETE FROM topics WHERE id = ?";

    private static final String DROP_TRIGGER_DELETE_TOPIC_REPORTS = "DROP TRIGGER IF EXISTS delete_report_by_topic";

    private static final String CREATE_TRIGGER_DELETE_REPORT_TOPIC = "" + "CREATE TRIGGER \n" + "delete_report_by_topic\n" + "BEFORE DELETE ON topics FOR EACH ROW \n" + "BEGIN \n" + "DELETE FROM reports WHERE id_topic = old.id;\n" + "END;";

    static {
        logger.info("TopicDAOImpl: dropping trigger.");
        try (Statement statement = DAOFactoryImpl.getConnection().createStatement()) {
            statement.execute(DROP_TRIGGER_DELETE_TOPIC_REPORTS);
        } catch (SQLException | NamingException e) {
            logger.error("TopicDAOImpl: exception({}) during dropping trigger delete_report_by_topic.", e.getMessage());
        }
    }

    static {
        logger.info("TopicDAOImpl: creating trigger.");
        try (Statement statement = DAOFactoryImpl.getConnection().createStatement()) {
            statement.execute(CREATE_TRIGGER_DELETE_REPORT_TOPIC);
        } catch (SQLException | NamingException e) {
            logger.error("TopicDAOImpl: exception({}) during creating trigger delete_report_by_topic.", e.getMessage());
        }
    }

    @Override
    public List<Topic> findAll(Connection connection) throws DBException {
        logger.info("TopicDAOImpl: get all topics.");
        List<Topic> topicList;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_TOPICS);
            topicList = extractTopicList(resultSet);
        } catch (SQLException e) {
            logger.error("TopicDAOImpl: exception ({}) during obtaining all topics.", e.getMessage());
            throw new DBException(e);
        }
        logger.info("TopicDAOImpl: all topics were obtained successfully.");
        return topicList;
    }

    @Override
    public void deleteTopic(Connection connection, long id) throws DBException {
        logger.info("TopicDAOImpl: delete topic with id = {}.", id);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_TOPIC_BY_ID)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            logger.error("TopicDAOImpl: exception ({}) during removing topic.", e.getMessage());
            throw new DBException(e);
        }
        logger.info("TopicDAOImpl: topic were removed successfully.");
    }

    private List<Topic> extractTopicList(ResultSet resultSet) throws SQLException {
        List<Topic> topicList = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Topic> topic = Optional.of(extractTopic(resultSet));
            topic.ifPresent(topicList::add);
        }
        return topicList;
    }

    private Topic extractTopic(ResultSet resultSet) throws SQLException {
        Topic topic = new Topic();
        topic.setId(resultSet.getLong("id"));
        topic.setName(resultSet.getString("name"));
        return topic;
    }
}
