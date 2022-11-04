package com.epam.conferences.controller;

import com.epam.conferences.exception.NoElementsException;
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
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "EventListServlet", value = "/eventList")
public class EventListServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(EventListServlet.class);
    private static final int PAGE_SIZE = 3;
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
        String sort;
        String address;
        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 1;
            request.setAttribute("currentPage", 1);
        } else {
            page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("currentPage", page);
        }
        List<Event> eventList;
        try {
            if (request.getParameter("sort") == null) {
                eventList = eventService.findEvents(page, PAGE_SIZE);
            } else {
                sort = request.getParameter("sort");
                request.setAttribute("sort", sort);
                eventList = eventService.findEventsSorted(page, PAGE_SIZE, sort);
            }
            if (eventList.isEmpty()) {
                logger.error("EventListServlet: there are no events.");
                throw new NoElementsException("There are no events.");
            }
            request.setAttribute("maxPage", eventService.maxPage(PAGE_SIZE));
            request.setAttribute("now", LocalDateTime.now());
            request.setAttribute("eventList", eventList);
            address = PathUtil.EVENT_LIST_PAGE;
        } catch (ServiceException | NoElementsException e) {
            logger.error("EventListServlet: exception ({}) during finding events for page {}", e.getMessage(), page);
            request.setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            request.setAttribute("address", "/");
            address = PathUtil.ERROR_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }

}
