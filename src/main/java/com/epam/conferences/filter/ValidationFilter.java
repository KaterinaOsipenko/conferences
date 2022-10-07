package com.epam.conferences.filter;

import com.epam.conferences.util.PathUtil;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "ValidationFilter", urlPatterns = "/registration")
public class ValidationFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(ValidationFilter.class);

    public void init(FilterConfig config) {
        logger.info("ValidationFilter: validation filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("ValidationFilter: validation filter doFilter.");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean valid = EmailValidator.getInstance().isValid(email);

        if (password == null || "".equals(password) || email == null || "".equals(email)
                || lastname == null || "".equals(lastname) || firstname == null || "".equals(firstname)
                || role == null || "".equals(role)) {
            logger.error("ValidationFilter: exception during validation user input. Empty field.");
            request.setAttribute("ex", "Fields can`t be empty");
            request.getRequestDispatcher(PathUtil.REGISTRATION_PAGE).include(request, response);
        } else if (!valid) {
            logger.error("ValidationFilter: exception during validation user input. Email is not valid");
            request.setAttribute("ex", "Email is not valid!");
            request.getRequestDispatcher(PathUtil.REGISTRATION_PAGE).include(request, response);
        } else {
            logger.info("ValidationFilter: data validated successfully.");
            chain.doFilter(request, response);
        }
    }
}
