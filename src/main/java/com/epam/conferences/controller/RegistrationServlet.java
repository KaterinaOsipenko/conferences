package com.epam.conferences.controller;

import com.epam.conferences.exception.ServiceException;
import com.epam.conferences.model.Role;
import com.epam.conferences.model.User;
import com.epam.conferences.security.PasswordEncoder;
import com.epam.conferences.service.UserService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("RegistrationServlet: doGet method.");
        HttpSession session = req.getSession();
        if (session.getAttribute("ex") != null) {
            session.removeAttribute("ex");
        }
        req.getRequestDispatcher(PathUtil.REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("RegistrationServlet: doPost method.");
        HttpSession session = req.getSession();
        String address;
        try {
            String email = req.getParameter("email");
            User user = userService.findUserByEmail(email);
            if (user != null && user.getRoleId() != 3) {
                logger.error("RegisterCommand: there is user with this email. ");
                session.setAttribute("ex", "There is user with this email. Please login.");
                address = PathUtil.LOGIN_PAGE;
            } else if (user != null && user.getRoleId() == 3) {
                String firstname = req.getParameter("firstname");
                String lastname = req.getParameter("lastname");
                String role = req.getParameter("role");
                char[] password = req.getParameter("password").toCharArray();
                int roleId = Role.valueOf(role).id;

                user.setRoleId(roleId);
                user.setPassword(password);
                user.setFirstName(firstname);
                user.setLastName(lastname);

                userService.updateUser(user);
                session.setAttribute("user", user);
                address = PathUtil.PROFILE_PAGE;
            } else {
                String firstname = req.getParameter("firstname");
                String lastname = req.getParameter("lastname");
                String role = req.getParameter("role");
                char[] password = req.getParameter("password").toCharArray();
                int roleId = Role.valueOf(role).id;

                User newUser = new User();
                newUser.setFirstName(firstname);
                newUser.setLastName(lastname);
                newUser.setEmail(email);
                newUser.setPassword(PasswordEncoder.encryptPassword(password));
                newUser.setRoleId(roleId);

                userService.saveUser(newUser);
                address = URLUtil.LOGIN_URL;
            }
        } catch (ServiceException e) {
            logger.error("RegisterCommand: exception while registration command was in work " + e.getMessage());
            req.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ERROR_PAGE;
        }
        logger.info("RegisterCommand: registration command finished.");
        resp.sendRedirect(address);
    }
}
