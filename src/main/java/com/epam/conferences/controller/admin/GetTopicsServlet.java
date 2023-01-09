package com.epam.conferences.controller.admin;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Topic;
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
import java.util.List;

@WebServlet(name = "GetTopicsServlet", value = "/admin/topics")
public class GetTopicsServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(GetTopicsServlet.class);

    private TopicService topicService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        topicService = (TopicService) config.getServletContext().getAttribute("topicService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GetTopicsServlet: doGet method.");
        List<Topic> topicList;
        String address;
        try {
            topicList = topicService.findAll();
            request.setAttribute("topics", topicList);
            address = PathUtil.ADMIN_VIEW_TOPICS_PAGE;
        } catch (ServiceException e) {
            logger.error("GetTopicsServlet: exception ({}).", e.getMessage());
            request.setAttribute("ex",
                    "There are no topics.");
            request.setAttribute("address", URLUtil.ADMIN_HOME);
            address = PathUtil.ADMIN_ERROR_PAGE;
        }
        request.getRequestDispatcher(address).forward(request, response);
    }


}
