package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.service.TopicService;
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

@WebServlet(name = "DeleteTopicServlet", value = "/admin/deleteTopic")
public class DeleteTopicServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DeleteTopicServlet.class);

    private TopicService topicService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        topicService = (TopicService) config.getServletContext().getAttribute("topicService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("DeleteTopicServlet: doPost method.");
        long id = Long.parseLong(request.getParameter("topicId"));
        String address;
        try {
            topicService.deleteTopic(id);
            address = "/admin/topics";
        } catch (ServiceException e) {
            logger.error("DeleteTopicServlet: exception ({}) during removing topic with id = {}", e.getMessage(), id);
            request.getSession().setAttribute("address", URLUtil.ADMIN_TOPICS);
            request.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        response.sendRedirect(address);
    }
}
