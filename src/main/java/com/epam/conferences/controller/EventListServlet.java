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
import java.util.List;

@WebServlet(name = "EventListServlet", value = "/eventListServlet")
public class EventListServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(EventListServlet.class);
    private EventService eventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("EventListServlet: doGet method.");
        int page;
        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 1;
            request.setAttribute("currentPage", 1);
        } else {
            page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("currentPage", page);
        }
        List<Event> eventList = null;
        try {
            request.setAttribute("maxPage", eventService.maxPage());
            eventList = eventService.findEvents(page);
        } catch (ServiceException e) {
            logger.error("EventListServlet: exception ({}) during finding events for page {}", e.getMessage(), page);
            request.setAttribute("ex", e.getMessage());
        }
        logger.info("EventListServlet: forwarding to {}", PathUtil.EVENT_LIST_PAGE);
        request.setAttribute("eventList", eventList);
        request.getRequestDispatcher(PathUtil.EVENT_LIST_PAGE).forward(request, response);
    }

}
