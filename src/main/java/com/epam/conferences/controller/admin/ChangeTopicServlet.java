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

@WebServlet(name = "ChangeTopicServlet", value = "/admin/changeTopic")
public class ChangeTopicServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ChangeTopicServlet.class);
    private ReportService reportService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("ChangeTopicServlet: doPost method.");
        int topicId = Integer.parseInt(request.getParameter("topicId"));
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String topicNewName = request.getParameter("nameTopic");
        String address;
        try {
            reportService.changeReportTopic(topicId, topicNewName);
            address = "/admin/getReports?id=" + eventId;
        } catch (ServiceException e) {
            logger.error("ChangeTopicServlet: exception ({}) during changing topic name with id={}", e.getMessage(), topicId);
            request.getSession().setAttribute("address", "admin/getReports?id=" + eventId);
            request.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        response.sendRedirect(address);
    }
}
