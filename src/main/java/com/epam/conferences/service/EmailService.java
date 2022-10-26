package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;

public interface EmailService {

    void send(String to) throws ServiceException;
}
