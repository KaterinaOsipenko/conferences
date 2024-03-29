package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.NoElementsException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Category;
import com.epam.conferences.model.Event;
import com.epam.conferences.service.CategoryService;
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
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ViewEventsServlet", value = "/admin/viewEvents")
public class ViewEventsServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ViewEventsServlet.class);
    private final static int PAGE_SIZE = 10;
    private EventService eventService;

    private CategoryService categoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
        categoryService = (CategoryService) config.getServletContext().getAttribute("categoryService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("ViewEventsServlet: doGet method.");
        String address;
        String sort = request.getParameter("sort");
        int page = getPagination(request);
        List<Event> eventList;
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
            List<Category> categories = categoryService.findAll();
            request.setAttribute("categories", categories);
            request.setAttribute("now", LocalDateTime.now());
            request.setAttribute("maxPage", eventService.maxPage(PAGE_SIZE));
            request.setAttribute("eventList", eventList);
            address = PathUtil.ADMIN_VIEW_EVENTS_PAGE;
        } catch (ServiceException | NoElementsException e) {
            logger.error("ViewEventsServlet: exception ({}) during finding events for admin page {}", e.getMessage(), page);
            request.setAttribute("ex", e instanceof NoElementsException ? e.getMessage() :
                    "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            request.setAttribute("address", URLUtil.ADMIN_VIEW_EVENTS);
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        logger.info("ViewEventsServlet: forward to view events page = {}", page);

        request.getRequestDispatcher(address).forward(request, response);
    }

    private int getPagination(HttpServletRequest request) {
        int page = 1;
        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            request.setAttribute("currentPage", 1);
        } else {
            page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("currentPage", page);
        }
        return page;
    }

}
