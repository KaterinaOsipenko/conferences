package com.epam.conferences.controller;

import com.epam.conferences.model.Role;
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

@WebServlet(name = "HomePageServlet", value = "/index.jsp")
public class HomePageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(HomePageServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("HomePageServlet: doGet.");
        String address;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRoleId() == Role.REG_USER.id) {
            address = PathUtil.HOME_PAGE;
        } else {
            if (user.getRoleId() == Role.MODERATOR.id) {
                address = PathUtil.ADMIN_HOME_PAGE;
            } else {
                address = PathUtil.SPEAKER_HOME_PAGE;
            }
        }
        request.getRequestDispatcher(address).forward(request, response);
    }

}
