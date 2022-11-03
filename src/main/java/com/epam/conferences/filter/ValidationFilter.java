package com.epam.conferences.filter;

import com.epam.conferences.util.PathUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@WebFilter(filterName = "ValidationFilter", urlPatterns = {"/registration", "/admin/editEvent"})
public class ValidationFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(ValidationFilter.class);

    public void init(FilterConfig config) {
        logger.info("ValidationFilter: validation filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("ValidationFilter: validation filter doFilter.");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (Objects.equals(req.getMethod(), "GET")) {
            chain.doFilter(request, response);
        } else {
            boolean valid = false;
            String address = null;
            String msg = null;
            String url = String.valueOf(req.getRequestURL());
            if (url.contains("registration")) {
                address = PathUtil.REGISTRATION_PAGE;
                msg = "Fields can`t be empty";
                valid = isValidUserRegistration(req);
            } else if (url.contains("editEvent")) {
                valid = isEventValid(req);
                msg = "Date must be in future.";
                address = "/admin/editEvent?id=" + req.getParameter("id");
                logger.info(address);
            }
            if (!valid) {
                logger.error("ValidationFilter: exception {} during validation user input.", msg);
                req.getSession().setAttribute("ex", msg);
                resp.sendRedirect(address);
            } else {
                logger.info("ValidationFilter: data validated successfully.");
                chain.doFilter(request, response);
            }
        }
    }

    private boolean isEventValid(HttpServletRequest request) {
        String date = request.getParameter("date");
        LocalDate dateEvent = LocalDate.parse(date);
        return !dateEvent.isBefore(LocalDate.now());
    }

    private boolean isValidUserRegistration(HttpServletRequest request) {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        return password != null && !"".equals(password) && email != null && !"".equals(email)
                && lastname != null && !"".equals(lastname) && firstname != null && !"".equals(firstname)
                && role != null && !"".equals(role);
    }
}
