package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.ServiceException;
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

@WebServlet(name = "EditEventServlet", value = {"/admin/editEvent", "/admin/cardEvent"})
public class EditEventServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(EditEventServlet.class);
    private EventService eventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("EditEventServlet: doGet method.");
        String address;
        int eventId = Integer.parseInt(request.getParameter("id"));
        try {
            Event event = eventService.findEvent(eventId);
            request.setAttribute("event", event);
            request.setAttribute("now", LocalDateTime.now());
            address = request.getRequestURI().contains("card") ? PathUtil.ADMIN_CARD_EVENT_PAGE : PathUtil.ADMIN_EDIT_EVENT_PAGE;
        } catch (ServiceException e) {
            logger.error("EditEventServlet: exception ({}) during getting event with id={}", e.getMessage(), eventId);
            request.setAttribute("address", URLUtil.ADMIN_CARD_EVENT + "?id=" + eventId);
            request.setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("EditEventServlet: doPost method.");

        if (request.getSession().getAttribute("ex") != null) {
            request.getSession().removeAttribute("ex");
        }

        int eventId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("about");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        LocalDate dateEvent = LocalDate.parse(date);
        LocalTime timeEvent = LocalTime.parse(time);
        LocalDateTime dateTime = LocalDateTime.of(dateEvent, timeEvent);
        String address;
        Event event = new Event.EventBuilder().setName(name).setDate(dateTime).setDescription(description).build();
        try {
            eventService.updateEvent(eventId, event);
            address = URLUtil.ADMIN_CARD_EVENT + "?id=" + eventId;
        } catch (ServiceException e) {
            logger.error("EditEventServlet: exception ({}) during updating event with id={}", e.getMessage(), eventId);
            request.getSession().setAttribute("address", "admin/getReports?id=" + eventId);
            request.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        response.sendRedirect(address);
    }
}
