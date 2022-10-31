package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;

public interface EmailService {

    void send(String to, String subject, String content) throws ServiceException;
}
