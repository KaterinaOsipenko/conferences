package com.epam.conferences.controller;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.User;
import com.epam.conferences.security.PasswordEncoder;
import com.epam.conferences.service.UserService;
import com.epam.conferences.util.PathUtil;
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
import java.util.Arrays;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("LoginServlet: doPost method.");
        String email = request.getParameter("email");
        String address;
        HttpSession session = request.getSession();
        try {
            User user = userService.findUserByEmail(email);
            if (user != null) {
                char[] password = request.getParameter("password").toCharArray();
                if (Arrays.equals(user.getPassword(), PasswordEncoder.encryptPassword(password))) {
                    session.setAttribute("user", user);
                    logger.info("LoginServlet: user with id {} and email {} wad logged in.", user.getId(), user.getEmail());
                    address = PathUtil.PROFILE_PAGE;
                } else {
                    logger.error("LoginServlet: invalid password exception.");
                    session.setAttribute("ex", "Invalid password!");
                    address = PathUtil.LOGIN_PAGE;
                }
            } else {
                session.setAttribute("ex", "There is no user with this email. PLease, create account.");
                address = PathUtil.REGISTRATION_PAGE;
            }
        } catch (ServiceException e) {
            logger.error("LoginServlet: exception while login command was in work " + e.getMessage());
            session.setAttribute("ex", e);
            address = PathUtil.ERROR_PAGE;
        }
        response.sendRedirect(address);
    }
}
