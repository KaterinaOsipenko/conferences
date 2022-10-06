package com.epam.conferences.controller;

import com.epam.conferences.controller.command.Command;
import com.epam.conferences.controller.command.CommandHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/entry")
public class AuthorizationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AuthorizationServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        logger.info("HelloServlet: doGet method started.");
        String address = "error.jsp";
        String commandName = req.getParameter("command");
        Command command = CommandHolder.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            logger.error("AuthorizationServlet: doGet exception" + ex.getMessage());
            req.setAttribute("exception", ex);
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("AuthorizationServlet: doPost method started.");
        String address = "error.jsp";
        String commandName = req.getParameter("command");
        Command command = CommandHolder.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            logger.error("AuthorizationServlet: doPost exception" + ex.getMessage());
            req.getSession().setAttribute("exception", ex);
        }
        resp.sendRedirect(address);
    }
}