package com.epam.conferences.controller.admin.speaker;

import com.epam.conferences.exception.NoElementsException;
import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Report;
import com.epam.conferences.model.User;
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

@WebServlet(name = "SpeakerEventsServlet", value = "/speaker/viewReports")
public class SpeakerReportsServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(SpeakerReportsServlet.class);

    private ReportService reportService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reportService = (ReportService) config.getServletContext().getAttribute("reportService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("SpeakerEventsServlet: doGet.");
        User user = (User) request.getSession().getAttribute("user");
        List<Report> reports;
        String address;
        try {
            reports = reportService.findReportsByUser((int) user.getId());
            if (reports.isEmpty()) {
                logger.error("SpeakerEventsServlet: there are no events.");
                throw new NoElementsException("You haven`t reports.");
            }
            request.setAttribute("reports", reports);
            address = PathUtil.SPEAKER_REPORTS_PAGE;
        } catch (ServiceException | NoElementsException e) {
            logger.error("SpeakerEventsServlet: exception ({}) during finding reports.", e.getMessage());
            request.setAttribute("ex", e instanceof NoElementsException ? e.getMessage() :
                    "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            request.setAttribute("address", PathUtil.INDEX_PAGE);
            address = PathUtil.SPEAKER_ERROR_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }


}
