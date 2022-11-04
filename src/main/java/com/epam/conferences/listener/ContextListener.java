package com.epam.conferences.listener;

import com.epam.conferences.dao.EventDAO;
import com.epam.conferences.dao.ReportDAO;
import com.epam.conferences.dao.UserDAO;
import com.epam.conferences.dao.UserEventDAO;
import com.epam.conferences.dao.impl.DAOFactoryImpl;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.service.*;
import com.epam.conferences.service.impl.*;
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
        ReportDAO reportDAO = DAOFactoryImpl.getInstance().getReportDao();
        UserEventDAO userEventDAO = DAOFactoryImpl.getInstance().getUserEventDao();

        NotificationManager userManager = new NotificationManager();
        NotificationManager eventManager = new NotificationManager();
        NotificationManager userEventManager = new NotificationManager();

        EmailService emailService = new EmailServiceImpl("587", "smtp.gmail.com");

        userManager.addObserver("registration", new RegistrationListener(emailService));
        userManager.addObserver("registrationToEvent", new RegistrationToEventListener(emailService));
        eventManager.addObserver("updateEvent", new UpdateEventListener(emailService));
        userEventManager.addObserver("registrationToEvent", new RegistrationToEventListener(emailService));

        UserService userService = new UserServiceImpl(userDAO, userManager);
        EventService eventService = new EventServiceImpl(eventDAO, userEventDAO, eventManager);
        ReportService reportService = new ReportServiceImpl(reportDAO);
        UserEventService userEventService = new UserEventServiceImpl(userEventDAO, userEventManager);


        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("eventService", eventService);
        servletContext.setAttribute("reportService", reportService);
        servletContext.setAttribute("userEventService", userEventService);
    }


}
