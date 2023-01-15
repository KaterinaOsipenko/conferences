package com.epam.conferences.controller.admin;

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

@WebServlet(name = "RegistrationSpeakerServlet", value = "/admin/registration")
public class RegistrationSpeakerServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(RegistrationSpeakerServlet.class);
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("RegistrationSpeakerServlet: doGet method.");
        HttpSession session = req.getSession();
        if (session.getAttribute("ex") != null) {
            session.removeAttribute("ex");
        }
        req.getRequestDispatcher(PathUtil.ADMIN_REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("RegistrationSpeakerServlet: doPost method.");
        HttpSession session = req.getSession();
        if (session.getAttribute("ex") != null) {
            session.removeAttribute("ex");
        }
        String address;
        try {
            String email = req.getParameter("email");
            User user = userService.findUserByEmail(email);
            if (user != null) {
                if (user.getRoleId() == 2) {
                    logger.error("RegistrationSpeakerServlet: there is speaker with this email. ");
                    session.setAttribute("ex", "There is speaker with this email.");
                    address = PathUtil.ADMIN_REGISTRATION_PAGE;
                } else if (user.getRoleId() == 3) {
                    userService.updateUser(extractUser(req, user));
                    session.setAttribute("user", user);
                    address = URLUtil.ADMIN_HOME;
                } else {
                    logger.error("RegistrationSpeakerServlet: this email use moderator.");
                    session.setAttribute("ex", "This email use moderator.");
                    address = PathUtil.ADMIN_REGISTRATION_PAGE;
                }
            } else {
                User newUser = extractUser(req, null);
                userService.saveUser(newUser);
                address = URLUtil.ADMIN_HOME;
            }
        } catch (ServiceException e) {
            logger.error("RegistrationSpeakerServlet: exception while registration command was in work " + e.getMessage());
            req.getSession().setAttribute("ex", "Sorry, we have some troubles. Our specialists have already tried to copy with this.");
            address = PathUtil.ERROR_PAGE;
        }
        logger.info("RegistrationSpeakerServlet: registration command finished.");
        resp.sendRedirect(address);
    }

    private User extractUser(HttpServletRequest req, User user) throws ServiceException {
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        char[] password = req.getParameter("password").toCharArray();
        int roleId = Role.SPEAKER.id;
        if (user == null) {
            user = new User();
            user.setEmail(req.getParameter("email"));
        }
        user.setRoleId(roleId);
        user.setPassword(PasswordEncoder.encryptPassword(password));
        user.setFirstName(firstname);
        user.setLastName(lastname);
        return user;
    }
}
