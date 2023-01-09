package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.NoElementsException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Report;
import com.epam.conferences.service.EventService;
import com.epam.conferences.service.ReportService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetReportsServlet", value = "/admin/getReports")
public class GetReportsServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(GetReportsServlet.class);
    private ReportService reportService;
    private EventService eventService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
        eventService = (EventService) config.getServletContext().getAttribute("eventService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GetReportsServlet: doGet method.");
        int eventId = Integer.parseInt(request.getParameter("id"));
        String address;

        HttpSession session = request.getSession();
        if (session.getAttribute("ex") != null) session.removeAttribute("ex");
        else if (session.getAttribute("address") != null) {
            session.removeAttribute("address");
        }

        try {
            List<Report> reports = reportService.getReportsByEventId(eventId);
            if (reports.isEmpty()) {
                logger.error("EventListServlet: there are no events.");
                throw new NoElementsException("There are no reports for this event.");
            }
            if (eventService.isPastEvent(eventId)) {
                request.setAttribute("past", "past");
            }
            request.setAttribute("reports", reports);
            request.setAttribute("eventId", eventId);
            address = PathUtil.ADMIN_VIEW_EVENT_REPORTS_PAGE;
        } catch (ServiceException | NoElementsException e) {
            logger.error("GetReportsServlet: exception ({}) during finding reports.", e.getMessage());
            request.setAttribute("ex", e instanceof NoElementsException ? e.getMessage() :
                    "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            request.setAttribute("address", URLUtil.ADMIN_VIEW_EVENTS);
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }
}
