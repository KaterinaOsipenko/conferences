package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> findAll() throws ServiceException;

    void deleteTopic(long id) throws ServiceException;

}
