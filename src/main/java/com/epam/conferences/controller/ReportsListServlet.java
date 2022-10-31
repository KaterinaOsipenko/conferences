package com.epam.conferences.controller;

import com.epam.conferences.exception.NoElementsException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Report;
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
import java.util.List;

@WebServlet(name = "ReportsListServlet", value = "/reports")
public class ReportsListServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ReportsListServlet.class);
    private ReportService reportService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("ReportsListServlet: doGet method.");
        int eventId = Integer.parseInt(request.getParameter("id"));
        String address;
        try {
            List<Report> reportList = reportService.getReportsByEventId(eventId);
            if (reportList.isEmpty()) {
                logger.error("ReportsListServlet: there are no reports for this event.");
                throw new NoElementsException("There are no reports for this event");
            } else {
                request.setAttribute("reports", reportList);
                address = PathUtil.REPORTS_LIST_PAGE;
            }
        } catch (ServiceException | NoElementsException e) {
            logger.error("ReportsListServlet: exception ({}) during find reports for event with id={}", e.getMessage(), eventId);
            request.setAttribute("ex", e.getMessage());
            request.setAttribute("address", "eventCardServlet");
            address = PathUtil.ERROR_PAGE;
        }
        request.setAttribute("id", eventId);
        logger.info("ReportsListServlet: get reports for event with id {}.", eventId);
        request.getRequestDispatcher(address).forward(request, response);
    }
}
