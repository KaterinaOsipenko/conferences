package com.epam.conferences.controller;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.model.User;
import com.epam.conferences.service.EventService;
import com.epam.conferences.service.UserEventService;
import com.epam.conferences.service.UserService;
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

@WebServlet(name = "ChoseEventServlet", value = "/chooseEvent")
public class ChooseEventServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ChooseEventServlet.class);
    private EventService eventService;

    private UserService userService;

    private UserEventService userEventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
        userEventService = (UserEventService) config.getServletContext().getAttribute("userEventService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("ChooseEventServlet: doGet method.");
        String address;
        Event event;
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            event = eventService.findEvent(id);
            request.setAttribute("event", event);
            address = PathUtil.CHOOSE_EVENT_PAGE;
        } catch (ServiceException e) {
            logger.error("ChooseEventServlet: exception ({}) during find event with id={}", e.getMessage(), id);
            request.setAttribute("ex", "Sorry, you can`t choose this event(");
            request.setAttribute("address", "eventListServlet");
            address = PathUtil.ERROR_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("ChooseEventServlet: doPost method.");
        String address;
        String email = request.getParameter("email");
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        Event event = new Event.EventBuilder().setId(eventId).build();
        try {
            User user = userService.findUserByEmail(email);
            if (user != null) {
                if (userEventService.isUserRegisteredToEvent(event, user)) {
                    request.getSession().setAttribute("ex", "You have taken place in this event.");
                    request.getSession().setAttribute("address", "eventList");
                    address = PathUtil.ERROR_PAGE;
                } else {
                    event = eventService.findEvent(eventId);
                    userEventService.registerUserToEvent(event, user);
                    address = PathUtil.SUCCESS_PAGE;
                }
            } else {
                user = new User();
                user.setEmail(email);
                event = eventService.findEvent(eventId);
                userService.registerUserToEvent(event, user);
                address = PathUtil.SUCCESS_PAGE;
            }
        } catch (ServiceException e) {
            request.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            request.getSession().setAttribute("address", "eventList");
            address = PathUtil.ERROR_PAGE;
        }
        response.sendRedirect(address);
    }
}
