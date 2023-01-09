package com.epam.conferences.service.impl;

import com.epam.conferences.dao.TopicDAO;
import com.epam.conferences.dao.impl.DAOFactoryImpl;
import com.epam.conferences.exception.DBException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Topic;
import com.epam.conferences.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class TopicServiceImpl implements TopicService {

    private static final Logger logger = LogManager.getLogger(TopicServiceImpl.class);

    private final TopicDAO topicDAO;

    public TopicServiceImpl(TopicDAO topicDAO) {
        this.topicDAO = topicDAO;
    }

    @Override
    public List<Topic> findAll() throws ServiceException {
        logger.info("TopicServiceImpl: get all topics.");
        List<Topic> topicList;
        try {
            topicList = topicDAO.findAll(DAOFactoryImpl.getConnection());
            if (topicList == null) {
                logger.error("TopicServiceImpl: topicList is NULL.");
                throw new ServiceException("TopicServiceImpl: topicList is NULL.");
            }
        } catch (DBException | NamingException | SQLException e) {
            logger.error("TopicServiceImpl: exception ({}) during obtaining all topics.", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("TopicServiceImpl: all topics were obtained successfully.");
        return topicList;
    }

    @Override
    public void deleteTopic(long id) throws ServiceException {
        logger.info("TopicServiceImpl: delete topics with id {}.", id);
        try {
            topicDAO.deleteTopic(DAOFactoryImpl.getConnection(), id);
        } catch (DBException | NamingException | SQLException e) {
            logger.error("TopicServiceImpl: exception ({}) during removing topic.", e.getMessage());
            throw new ServiceException(e);
        }
        logger.info("TopicServiceImpl: topic was removed successfully.");

    }
}
