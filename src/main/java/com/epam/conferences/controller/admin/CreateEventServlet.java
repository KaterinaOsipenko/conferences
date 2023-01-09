package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Address;
import com.epam.conferences.model.Event;
import com.epam.conferences.service.EventService;
import com.epam.conferences.util.PathUtil;
import com.epam.conferences.util.URLUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@WebServlet(name = "CreateEventServlet", value = "/admin/createEvent")
public class CreateEventServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CreateEventServlet.class);

    private EventService eventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("CreateEventServlet: doGet method.");
        logger.info("CreateEventServlet: forward to form page.");
        request.getRequestDispatcher(PathUtil.ADMIN_CREATE_EVENT_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("CreateEventServlet: doPost method.");
        Event event = extractEvent(request);
        String address;
        try {
            int eventId = eventService.createEvent(event);
            address = URLUtil.ADMIN_CARD_EVENT + "?id=" + eventId;
        } catch (ServiceException e) {
            logger.error("CreateEventServlet: exception ({}) during creating event", e.getMessage());
            request.getSession().setAttribute("address", URLUtil.ADMIN_HOME);
            request.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        response.sendRedirect(address);
    }

    private Event extractEvent(HttpServletRequest request) {
        logger.info("CreateEventServlet: extractEvent method.");
        String name = request.getParameter("name");
        String description = request.getParameter("about");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        LocalDate dateEvent = LocalDate.parse(date);
        LocalTime timeEvent = LocalTime.parse(time);
        LocalDateTime dateTime = LocalDateTime.of(dateEvent, timeEvent);
        Address address = extractAddress(request);
        return new Event.EventBuilder().setName(name).setDescription(description)
                .setAddress(address).setDate(dateTime).build();
    }

    private Address extractAddress(HttpServletRequest request) {
        logger.info("CreateEventServlet: extractAddress method.");
        Address address = new Address();
        address.setCountry(request.getParameter("country"));
        address.setCity(request.getParameter("city"));
        address.setStreet(request.getParameter("street"));
        address.setHouse(Integer.parseInt(request.getParameter("house")));
        return address;
    }
}
