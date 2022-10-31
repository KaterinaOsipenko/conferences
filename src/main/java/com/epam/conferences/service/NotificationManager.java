package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.listener.NotificationListener;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class NotificationManager {

    private static final Logger logger = LogManager.getLogger(NotificationManager.class);

    private Map<String, NotificationListener> observers = new HashMap<>();

    public void addObserver(String notifyType, NotificationListener listener) {
        logger.info("NotificationManager: add observer for event {}.", notifyType);
        observers.put(notifyType, listener);
    }

    public void removeObserver(String notifyType) {
        logger.info("NotificationManager: remove observer for event {}.", notifyType);
        observers.remove(notifyType);
    }

    public Map<String, NotificationListener> getObservers() {
        return observers;
    }

    public void setObservers(Map<String, NotificationListener> observers) {
        this.observers = observers;
    }

    public void notify(String notifyType, User user, Event event) throws ServiceException {
        logger.info("NotificationManager: notify observer for event {} with user({}).", notifyType, user.getId());
        NotificationListener listener = observers.get(notifyType);
        listener.notify(user, event);
    }
}
