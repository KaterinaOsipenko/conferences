package com.epam.conferences.controller.admin;

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
import java.util.List;

@WebServlet(name = "ViewEventsServlet", value = "/admin/viewEvents")
public class ViewEventsServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ViewEventsServlet.class);
    private final static int PAGE_SIZE = 10;
    private EventService eventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("ViewEventsServlet: doGet method.");
        String address;
        String sort = request.getParameter("sort");
        int page;
        List<Event> eventList;
        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 1;
            request.setAttribute("currentPage", 1);
        } else {
            page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("currentPage", page);
        }
        try {
            if (sort == null) {
                sort = "future";
                eventList = eventService.findEventsSorted(page, PAGE_SIZE, sort);
            } else if (sort.equals("all")) {
                eventList = eventService.findEvents(page, PAGE_SIZE);
            } else {
                eventList = eventService.findEventsSorted(page, PAGE_SIZE, sort);
            }
            if (eventList.isEmpty()) {
                logger.error("EventListServlet: there are no events.");
                throw new NoElementsException("There are no events.");
            }
            if (sort.equals("past")) request.setAttribute("past", "past");
            request.setAttribute("maxPage", eventService.maxPage(PAGE_SIZE));
            request.setAttribute("eventList", eventList);
            address = PathUtil.ADMIN_VIEW_EVENTS_PAGE;
        } catch (ServiceException | NoElementsException e) {
            logger.error("ViewEventsServlet: exception ({}) during finding events for admin page {}", e.getMessage(), page);
            request.setAttribute("ex", e instanceof NoElementsException ? e.getMessage() :
                    "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            request.setAttribute("address", "admin/viewEvents");
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        logger.info("ViewEventsServlet: forward to view events page = {}", page);

        request.getRequestDispatcher(address).forward(request, response);
    }

}
