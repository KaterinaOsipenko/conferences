package com.epam.conferences.controller;

import com.epam.conferences.model.User;
import com.epam.conferences.util.PathUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("LogoutServlet: doGet method start.");
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().invalidate();
        logger.info("LogoutServlet: user {} logout. Forwarding to home page.", user);
        request.getRequestDispatcher(PathUtil.HOME_PAGE).forward(request, response);
    }
}
