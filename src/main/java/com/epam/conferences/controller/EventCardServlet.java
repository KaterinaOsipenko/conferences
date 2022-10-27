package com.epam.conferences.controller;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Event;
import com.epam.conferences.service.EventService;
import com.epam.conferences.service.ReportService;
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

@WebServlet(name = "EventCardServlet", value = "/eventCardServlet")
public class EventCardServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EventCardServlet.class);
    private EventService eventService;

    private ReportService reportService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("EventCardServlet: doGet method.");
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event;
        String address;
        try {
            event = eventService.findEvent(eventId);
            if (event == null) {
                logger.error("EventCardServlet: event with id={} null", eventId);
                request.setAttribute("ex", "There is no this event.");
                address = PathUtil.EVENT_LIST_PAGE;
            } else {
                request.setAttribute("reports", reportService.getReportsByEventId(event));
                request.setAttribute("event", event);
                address = PathUtil.EVENT_CARD_PAGE;
            }
        } catch (ServiceException e) {
            logger.error("EventCardServlet: exception ({}) during find event with id={}", e.getMessage(), eventId);
            request.setAttribute("ex", e.getMessage());
            address = PathUtil.EVENT_LIST_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }

}