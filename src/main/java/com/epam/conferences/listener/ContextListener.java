package com.epam.conferences.listener;

import com.epam.conferences.dao.EventDAO;
import com.epam.conferences.dao.UserDAO;
import com.epam.conferences.dao.impl.DAOFactoryImpl;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.service.EmailService;
import com.epam.conferences.service.EventService;
import com.epam.conferences.service.NotificationManager;
import com.epam.conferences.service.UserService;
import com.epam.conferences.service.impl.EmailServiceImpl;
import com.epam.conferences.service.impl.EventServiceImpl;
import com.epam.conferences.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        final Logger logger = LogManager.getLogger(ContextListener.class);
        logger.info("ContextListener: Servlet Context initialized.");
        try {
            createServices(servletContext);
        } catch (ServiceException ex) {
            logger.error("ContextListener: exception during creating services.");

        }
        logger.info("ContextListener: creating services.");
    }

    private void createServices(ServletContext servletContext) throws ServiceException {
        UserDAO userDAO = DAOFactoryImpl.getInstance().getUserDao();
        EventDAO eventDAO = DAOFactoryImpl.getInstance().getEventDao();

        NotificationManager userManager = new NotificationManager();
        NotificationManager eventManager = new NotificationManager();

        EmailService emailService = new EmailServiceImpl("587", "smtp.gmail.com");

        userManager.addObserver("registration", new RegistrationListener(emailService));
        userManager.addObserver("registrationToEvent", new RegistrationToEventListener(emailService));
        eventManager.addObserver("registrationToEvent", new RegistrationToEventListener(emailService));

        UserService userService = new UserServiceImpl(userDAO, userManager);
        EventService eventService = new EventServiceImpl(eventDAO, eventManager);

        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("eventService", eventService);
    }


}
