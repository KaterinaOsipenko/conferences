package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.ServiceException;
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

@WebServlet(name = "DeleteReportServlet", value = "/admin/deleteReport")
public class DeleteReportServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(DeleteReportServlet.class);
    private ReportService reportService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("DeleteReportServlet: doPost method.");
        int reportId = Integer.parseInt(request.getParameter("reportId"));
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String address;
        try {
            reportService.deleteReport(reportId);
            address = "/admin/getReports?id=" + eventId;
        } catch (ServiceException e) {
            logger.error("DeleteReportServlet: exception ({}) during removing report with id={}", e.getMessage(), reportId);
            request.setAttribute("address", "admin/getReports?id=" + eventId);
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        response.sendRedirect(address);
    }


}
