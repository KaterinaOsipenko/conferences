package com.epam.conferences.controller;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.service.EventService;
import com.epam.conferences.util.PathUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EventCardServlet", value = "/eventCard")
public class EventCardServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EventCardServlet.class);
    private EventService eventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("EventCardServlet: doGet method.");
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event;
        String address;
        try {
            event = eventService.findEvent(eventId);
            request.setAttribute("event", event);
            address = PathUtil.EVENT_CARD_PAGE;
        } catch (ServiceException e) {
            logger.error("EventCardServlet: exception ({}) during find event with id={}", e.getMessage(), eventId);
            request.setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.EVENT_LIST_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }

}
