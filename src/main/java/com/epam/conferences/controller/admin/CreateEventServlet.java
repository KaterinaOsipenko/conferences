package com.epam.conferences.controller.admin;

import com.epam.conferences.util.PathUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateEventServlet", value = "/admin/createEvent")
public class CreateEventServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CreateEventServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("CreateEventServlet: doGet method.");
        logger.info("CreateEventServlet: forward to form page.");
        request.getRequestDispatcher(PathUtil.ADMIN_CREATE_EVENT_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("CreateEventServlet: doPost method.");
    }
}
