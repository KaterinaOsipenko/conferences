package com.epam.conferences.service;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.listener.NotificationListener;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;

import java.util.HashMap;
import java.util.Map;

public class NotificationManager {

    private Map<String, NotificationListener> observers = new HashMap<>();

    public void addObserver(String notifyType, NotificationListener listener) {
        observers.put(notifyType, listener);
    }

    public void removeObserver(String notifyType) {
        observers.remove(notifyType);
    }

    public Map<String, NotificationListener> getObservers() {
        return observers;
    }

    public void setObservers(Map<String, NotificationListener> observers) {
        this.observers = observers;
    }

    public void notify(String notifyType, User user, Event event) throws ServiceException {
        NotificationListener listener = observers.get(notifyType);
        listener.notify(user, event);
    }
}
